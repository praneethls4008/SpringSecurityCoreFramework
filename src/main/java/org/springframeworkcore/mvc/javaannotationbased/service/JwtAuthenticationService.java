package org.springframeworkcore.mvc.javaannotationbased.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAuthenticationService {
    public String authenticateAndGenerateToken(String username, String password);
    public String generateToken(UserDetails userDetails);
}
