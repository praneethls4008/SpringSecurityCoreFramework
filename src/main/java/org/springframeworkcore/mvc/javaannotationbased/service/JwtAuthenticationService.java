package org.springframeworkcore.mvc.javaannotationbased.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtAuthenticationService {
    public Map<String, String>  authenticateAndGenerateTokens(String username, String password);
    public Map<String, String> generateAccessAndRefreshTokens(UserDetails userDetails);
    public String refreshAccessToken(String refreshToken);
}
