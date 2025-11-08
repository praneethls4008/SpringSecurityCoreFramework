package org.springframeworkcore.mvc.javaannotationbased.controller.jwt;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.jwt.AuthRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.jwt.RegisterRequest;
import org.springframeworkcore.mvc.javaannotationbased.service.JwtAuthenticationService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    private final JwtAuthenticationService jwtAuthenticationService;
    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public JwtController(JwtAuthenticationService jwtAuthenticationService, UserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder){
        this.jwtAuthenticationService = jwtAuthenticationService;
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest authRequest) {
        System.out.println("in jwt auth login");
        String token = jwtAuthenticationService.authenticateAndGenerateToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        System.out.println("in jwt auth register");
        if (userDetailsManager.userExists(request.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        String role = (request.getRole() != null) ? request.getRole() : "USER";

        UserDetails newUser = User.withUsername(request.getUsername())
                .password(encodedPassword)
                .roles(role)
                .build();

        userDetailsManager.createUser(newUser);

        // Optionally generate a token right after registration
        String token = jwtAuthenticationService.generateToken(newUser);

        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "username", request.getUsername(),
                "token", token
        ));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> dashboard(Authentication authentication) {
        // The Authentication object is automatically injected by Spring Security
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Welcome to your dashboard!");
        response.put("username", authentication.getName());
        response.put("roles", authentication.getAuthorities());
        response.put("issuedAt", new Date());

        return ResponseEntity.ok(response);
    }






}
