package com.mapto.api.config.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {
    private final String tokenType = "Bearer";
    private final String accessToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;

    public JwtAuthenticationResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
