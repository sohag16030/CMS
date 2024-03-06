package com.cms.example.cms.auth.config;


import com.cms.example.cms.auth.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
@RequiredArgsConstructor
public class JwtTokenServiceInterceptorAppConfig extends WebMvcConfigurerAdapter {

    private final JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/user/accessToken","/user/getAccessTokenFromRefreshToken","/api/cmsUser");
    }
}
