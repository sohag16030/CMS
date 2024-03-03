package com.cms.example.cms.auth.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cms.example.cms.App;
import com.cms.example.cms.auth.repository.BlackListedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private static final Logger logger = LogManager.getLogger(App.class);

    private final BlackListedTokenRepository blackListedTokenRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("Pre Handle method is Calling");
        String token = extractTokenFromHeader(request.getHeader("Authorization"));
        if (token == null || !validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private String extractTokenFromHeader(String header) {
        // Check if the header is null or does not start with "Bearer "
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        // Extract and return the token
        return header.substring(7);
    }

    public boolean validateToken(String token) {
        if (blackListedTokenRepository.findByAccessToken(token) == null) {
            return true;
        } else return false;
    }

}

