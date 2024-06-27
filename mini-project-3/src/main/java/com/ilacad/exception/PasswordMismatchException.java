package com.ilacad.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException(String message) {
        super(message);
    }
}
