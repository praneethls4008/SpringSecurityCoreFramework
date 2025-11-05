package org.springframeworkcore.mvc.javaannotationbased.service;

import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.LoginRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.RefreshTokenRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.request.user.RegisterRequest;
import org.springframeworkcore.mvc.javaannotationbased.dto.response.user.AuthenticationResponse;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse login(LoginRequest request);
    public AuthenticationResponse refreshToken(RefreshTokenRequest request);
    public void logout(String token);
}
