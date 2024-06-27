package com.ilacad.mapper;

import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.LoginResponse;
import com.ilacad.dto.response.RegisterResponse;
import com.ilacad.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User registerRequestToUser(RegisterRequest registerRequest);

    RegisterResponse userToRegisterResponse(User user);

    LoginResponse userToLoginResponse(User user);
}
