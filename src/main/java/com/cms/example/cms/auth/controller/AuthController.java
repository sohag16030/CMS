package com.cms.example.cms.auth.controller;

import com.cms.example.cms.auth.model.AuthenticationRequest;
import com.cms.example.cms.auth.model.AuthenticationResponse;
import com.cms.example.cms.auth.model.NewAccessTokenRequest;
import com.cms.example.cms.auth.repository.BlackListedTokenRepository;
import com.cms.example.cms.auth.service.RefreshTokenService;
import com.cms.example.cms.auth.util.JwtUtil;
import com.cms.example.cms.entities.BlackListedToken;
import com.cms.example.cms.entities.CmsUser;
import com.cms.example.cms.entities.RefreshToken;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final RefreshTokenService refreshTokenService;
    private final BlackListedTokenRepository blackListedTokenRepository;
    private final CmsUserRepository repository;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> generateToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            // Authenticate user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            // Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Generate JWT token
        final String accesstoken = jwtTokenUtil.generateToken(authenticationRequest.getUserName());
        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticationRequest.getUserName());

        // Return token in response
        return ResponseEntity.ok(new AuthenticationResponse(accesstoken, refreshToken.getRefreshToken()));
    }

    @PostMapping("/getAccessTokenFromRefreshToken")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody NewAccessTokenRequest refreshTokenRequest) {
        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken()) // Find the refresh token in the database
                .map(refreshTokenService::verifyExpiration) // Verify if the refresh token has expired
                .map(RefreshToken::getUserInfo) // Extract the token from the RefreshToken object
                .map(userInfo -> {
                    String accessToken = jwtTokenUtil.generateToken(userInfo.getUserName());// Generate a new access token using the user information
                    return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshTokenRequest.getRefreshToken())); // Return a ResponseEntity with the new access token and the refresh token
                }).orElseThrow(() -> new RuntimeException("Refresh token is not in the database")); // Throw an exception if the refresh token is not found in the database
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader, Principal principal) {
        // Check if the authorization header starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token from the authorization header
            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

            BlackListedToken blackListedToken = new BlackListedToken();
            blackListedToken.setAccessToken(token);

            CmsUser user = getLoggedInUser(principal);
            blackListedToken.setUserInfo(user);

            // Add the token to the blacklist
            blackListedTokenRepository.save(blackListedToken);
            return ResponseEntity.ok("Logged out successfully");
        } else {
            // Invalid authorization header format
            return ResponseEntity.badRequest().body("Invalid authorization header format");
        }
    }
    private CmsUser getLoggedInUser(Principal principal) {
        return repository.findByUserName(principal.getName()).get();
    }
}
