package org.springframeworkcore.mvc.javaannotationbased.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframeworkcore.mvc.javaannotationbased.service.JwtAuthenticationService;
import org.springframeworkcore.mvc.javaannotationbased.service.JwtService;

import java.util.Map;

@Service
public class JwtAuthenticationServiceImplementation implements JwtAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsManager userDetailsManager;

    public JwtAuthenticationServiceImplementation(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsManager userDetailsManager){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsManager = userDetailsManager;
    }

    public Map<String, String> generateAccessAndRefreshTokens(UserDetails userDetails){
        return jwtService.generateAccessAndRefreshTokens(userDetails);
    }

    public String refreshAccessToken(String refreshToken){
        String username = jwtService.extractUsername(refreshToken);
        System.out.println("username extracted from refresh_token:"+username);
        UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
        System.out.println("username deatils:"+userDetails);
        return jwtService.refreshAccessToken(refreshToken, userDetails);
    }



    public Map<String, String>  authenticateAndGenerateTokens(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
            return jwtService.generateAccessAndRefreshTokens(userDetails);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

}
