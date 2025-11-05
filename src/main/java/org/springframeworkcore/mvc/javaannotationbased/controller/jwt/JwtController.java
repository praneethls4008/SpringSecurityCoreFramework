package org.springframeworkcore.mvc.javaannotationbased.controller.jwt;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.LoginRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.RefreshTokenRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.RegisterRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.user.AuthenticationResponse;
import org.springframeworkcore.mvc.javaannotationbased.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")

public class JwtController {
    private final AuthenticationService authenticationService;

    public JwtController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token.substring(7));
        return ResponseEntity.noContent().build();
    }
}
