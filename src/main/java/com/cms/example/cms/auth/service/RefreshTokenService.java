package com.cms.example.cms.auth.service;

import com.cms.example.cms.auth.repository.RefreshTokenRepository;
import com.cms.example.cms.entities.RefreshToken;
import com.cms.example.cms.feature.user.CmsUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final CmsUserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(userRepository.findByUserName(username).get())
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) //10 min
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + "RefreshToken was expired. Please make a new sign in request");
        }
        return token;
    }

    public void addToBlacklist(String token) {
        Set<String> blackListedToken = new HashSet<>();
        blackListedToken.add(token);
    }
}
