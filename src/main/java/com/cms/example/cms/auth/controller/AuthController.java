//package com.cms.example.cms.auth.controller;
//
//import com.cms.example.cms.auth.model.AuthenticationRequest;
//import com.cms.example.cms.auth.model.AuthenticationResponse;
//import com.cms.example.cms.auth.model.NewAccessTokenRequest;
//import com.cms.example.cms.auth.model.Role;
//import com.cms.example.cms.auth.repository.BlackListedTokenRepository;
//import com.cms.example.cms.auth.service.CmsUserDetailsService;
//import com.cms.example.cms.auth.service.RefreshTokenService;
//import com.cms.example.cms.auth.util.JwtUtil;
//import com.cms.example.cms.common.Routes;
//import com.cms.example.cms.entities.BlackListedToken;
//import com.cms.example.cms.entities.CmsUser;
//import com.cms.example.cms.entities.RefreshToken;
//import com.cms.example.cms.feature.user.CmsUserRepository;
//import lombok.RequiredArgsConstructor;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.security.Principal;
//import java.util.List;
//import java.util.Optional;
//
//
//@RestController
//@RequiredArgsConstructor
//public class AuthController {
//
//    private final AuthenticationManager authenticationManager;
//
//    private final JwtUtil jwtTokenUtil;
//
//    private final RefreshTokenService refreshTokenService;
//
//    private final BlackListedTokenRepository blackListedTokenRepository;
//
//    private final CmsUserRepository repository;
//
//    private final CmsUserDetailsService groupUserDetailsService;
//
//    private final CmsUserRepository userRepository;
//
//
//    @PostMapping(Routes.USER_LOGIN)
//    public ResponseEntity<AuthenticationResponse> generateToken(@RequestBody AuthenticationRequest authenticationRequest) {
//
//        try {
//            // Authenticate user
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
//        } catch (BadCredentialsException e) {
//            // Unauthorized
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        // Generate JWT token
//        final String accessToken = jwtTokenUtil.generateToken(authenticationRequest.getUserName());
//        final RefreshToken refreshToken = refreshTokenService.createRefreshToken(authenticationRequest.getUserName());
//
//        // Return token in response
//        return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken.getRefreshToken()));
//    }
//
//    @PostMapping(Routes.USER_ACCESS_TOKEN_FROM_REFRESH_TOKEN)
//    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody NewAccessTokenRequest refreshTokenRequest) {
//
//        return refreshTokenService.findByToken(refreshTokenRequest.getRefreshToken()) // Find the refresh token in the database
//                .map(refreshTokenService::verifyExpiration) // Verify if the refresh token has expired
//                .map(RefreshToken::getCmsUser) // Extract the token from the RefreshToken object
//                .map(userInfo -> {
//                    String accessToken = jwtTokenUtil.generateToken(userInfo.getUserName());// Generate a new access token using the user information
//                    return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshTokenRequest.getRefreshToken())); // Return a ResponseEntity with the new access token and the refresh token
//                }).orElseThrow(() -> new RuntimeException("Refresh token is not in the database")); // Throw an exception if the refresh token is not found in the database
//    }
//
//    @PutMapping(Routes.USER_ROLE_ASSIGNMENT)
//    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_MODERATOR')")
//    public ResponseEntity<?> giveAccessToUser(@PathVariable Long userId, @RequestBody Role newRole, Principal principal) {
//
//        Optional<CmsUser> cmsUser = userRepository.findById(userId);
//        CmsUser user;
//        if (cmsUser.isPresent()) {
//            user = cmsUser.get();
//            List<String> activeRoles = groupUserDetailsService.getRolesByLoggedInUser(principal);
//            String updatedRoles = null;
//            if (!user.getRoles().contains(newRole.getNewRole())) {
//                updatedRoles = user.getRoles() + "," + newRole.getNewRole();
//            }
//            try {
//                if (activeRoles.contains("ROLE_ADMIN")) {
//                    user.setRoles(updatedRoles);
//                } else if (activeRoles.contains("ROLE_MODERATOR") && newRole.getNewRole().contains("ROLE_MODERATOR")) {
//                    user.setRoles(updatedRoles);
//                } else {
//                    return new ResponseEntity<>("forbidden", HttpStatus.FORBIDDEN);
//                }
//                return new ResponseEntity<>(user, HttpStatus.OK);
//            } catch (Exception e) {
//                return new ResponseEntity<>("UPDATE FAILED", HttpStatus.NOT_FOUND);
//            }
//
//        } else {
//            // TODO : throw EntityNotFoundException
//            return new ResponseEntity<>("DATA NOT FOUND", HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//    @DeleteMapping(Routes.USER_LOGOUT)
//    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader, Principal principal) {
//
//        // Check if the authorization header starts with "Bearer "
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            // Extract the token from the authorization header
//            String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
//
//            BlackListedToken blackListedToken = new BlackListedToken();
//            blackListedToken.setAccessToken(token);
//
//            CmsUser user = getLoggedInUser(principal);
//            blackListedToken.setCmsUser(user);
//
//            // Add the token to the blacklist
//            blackListedTokenRepository.save(blackListedToken);
//            return ResponseEntity.ok("Logged out successfully");
//        } else {
//            // Invalid authorization header format
//            return ResponseEntity.badRequest().body("Invalid authorization header format");
//        }
//    }
//
//    private CmsUser getLoggedInUser(Principal principal) {
//        Optional<CmsUser> user = repository.findByUserName(principal.getName());
//        return user.orElse(null);
//    }
//}
