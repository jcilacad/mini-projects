package com.ilacad.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String email) {
        super(String.format("User not found with email: %s", email));
    }
}
