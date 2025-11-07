package org.springframeworkcore.mvc.javaannotationbased.implementation;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframeworkcore.mvc.javaannotationbased.dao.UserRepository;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.LoginRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.RefreshTokenRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.RegisterRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.user.AuthenticationResponse;
import org.springframeworkcore.mvc.javaannotationbased.model.User;
import org.springframeworkcore.mvc.javaannotationbased.jwt.JwtTokenProvider;

@Service
public class AuthenticationServiceImplementation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);

        userRepository.save(user);

        var accessToken = jwtTokenProvider.generateAccessToken(user);
        var refreshToken = jwtTokenProvider.generateRefreshToken(user);

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);
        authResponse.setTokenType("Bearer");
        return authResponse;
    }

    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        var accessToken = jwtTokenProvider.generateAccessToken(user);
        var refreshToken = jwtTokenProvider.generateRefreshToken(user);

        AuthenticationResponse authResponse = new AuthenticationResponse();
        authResponse.setAccessToken(accessToken);
        authResponse.setRefreshToken(refreshToken);
        authResponse.setTokenType("Bearer");
        return authResponse;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        final String refreshToken = request.getRefreshToken();
        final String username = jwtTokenProvider.extractUsername(refreshToken);

        if (username != null) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (jwtTokenProvider.isTokenValid(refreshToken, user)) {
                var accessToken = jwtTokenProvider.generateAccessToken(user);

                AuthenticationResponse authResponse = new AuthenticationResponse();
                authResponse.setAccessToken(accessToken);
                authResponse.setRefreshToken(refreshToken);
                authResponse.setTokenType("Bearer");
                return authResponse;
            }
        }
        throw new RuntimeException("Invalid refresh token");
    }

    public void logout(String token) {
        // Implement token blacklist logic here if needed
        // Could use Redis or database to store blacklisted tokens
    }
}
