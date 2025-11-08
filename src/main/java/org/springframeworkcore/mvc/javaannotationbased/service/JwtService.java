package org.springframeworkcore.mvc.javaannotationbased.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    public String generateToken(UserDetails userDetails);
    public String extractUsername(String token);
    public <T> T extractClaim(String token, Function<Claims, T> resolver);
    public boolean validateToken(String token, UserDetails userDetails);


}
