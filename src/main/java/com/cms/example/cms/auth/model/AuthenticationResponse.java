package com.cms.example.cms.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String access_token;
    private String refresh_token;

    public AuthenticationResponse(String access_token) {
        this.access_token = access_token;
    }
}

