package com.ilacad.controller;

import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.LoginResponse;
import com.ilacad.dto.response.RegisterResponse;
import com.ilacad.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    public RegisterResponse register(RegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}
