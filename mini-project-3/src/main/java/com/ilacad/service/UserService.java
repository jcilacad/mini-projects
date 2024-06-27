package com.ilacad.service;

import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.LoginResponse;
import com.ilacad.dto.response.RegisterResponse;

public interface UserService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
