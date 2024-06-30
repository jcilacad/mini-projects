package com.ilacad.service.impl;

import com.ilacad.dao.UserDao;
import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.LoginResponse;
import com.ilacad.dto.response.RegisterResponse;
import com.ilacad.entity.Cart;
import com.ilacad.entity.User;
import com.ilacad.enums.Role;
import com.ilacad.exception.*;
import com.ilacad.mapper.UserMapper;
import com.ilacad.service.UserService;
import com.ilacad.util.Hashing;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected Validator validator = factory.getValidator();

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            violations.forEach(v -> {
                String fieldName = v.getPropertyPath().toString();
                String errorMessage = v.getMessage();
                errors.put(fieldName, errorMessage);
            });
            throw new InvalidInputException(errors);
        }

        if (userDao.isUserFoundByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException(registerRequest.getEmail());
        }

        if (!registerRequest.isPasswordConfirmed()) {
            throw new PasswordMismatchException("Confirm password is not the same as password");
        }

        User user = UserMapper.INSTANCE.registerRequestToUser(registerRequest);
        user.setRole(Role.USER);
        user.setCredits(BigDecimal.ZERO);
        Cart cart = new Cart();
        user.setCart(cart);
        User savedUser = userDao.insertUser(user);
        return UserMapper.INSTANCE.userToRegisterResponse(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            violations.forEach(v -> {
                String fieldName = v.getPropertyPath().toString();
                String errorMessage = v.getMessage();
                errors.put(fieldName, errorMessage);
            });
            throw new InvalidInputException(errors);
        }

        User existingUser = userDao.findUserByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));
        String[] mutableHash = new String[1];
        Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };
        boolean isPasswordCorrect = Hashing.verifyAndUpdateHash(loginRequest.getPassword(), existingUser.getPassword(), update);
        if (!isPasswordCorrect) {
            throw new InvalidEmailOrPasswordException("Invalid email or password");
        }

        return UserMapper.INSTANCE.userToLoginResponse(existingUser);
    }


}
