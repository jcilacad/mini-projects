package com.ilacad.exception;

public class InvalidEmailOrPasswordException extends RuntimeException {

    public InvalidEmailOrPasswordException(String message) {
        super(message);
    }
}
