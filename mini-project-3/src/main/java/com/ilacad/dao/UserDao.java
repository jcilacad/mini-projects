package com.ilacad.dao;

import com.ilacad.entity.User;

import java.util.Optional;

public interface UserDao {

    User insertUser(User user);

    boolean isUserFoundByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
