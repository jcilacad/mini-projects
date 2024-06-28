package com.ilacad.dao;

import com.ilacad.entity.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao {

    User insertUser(User user);

    boolean isUserFoundByEmail(String email);

    Optional<User> findUserByEmail(String email);

    void updateCredits(Long userId, BigDecimal newCredits);
}
