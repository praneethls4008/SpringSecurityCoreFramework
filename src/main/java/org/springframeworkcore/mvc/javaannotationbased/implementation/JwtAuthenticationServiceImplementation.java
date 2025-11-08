package org.springframeworkcore.mvc.javaannotationbased.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframeworkcore.mvc.javaannotationbased.service.JwtAuthenticationService;
import org.springframeworkcore.mvc.javaannotationbased.service.JwtService;

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

    public String generateToken(UserDetails userDetails){
        return jwtService.generateToken(userDetails);
    }

    public String authenticateAndGenerateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
            return jwtService.generateToken(userDetails);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

}
