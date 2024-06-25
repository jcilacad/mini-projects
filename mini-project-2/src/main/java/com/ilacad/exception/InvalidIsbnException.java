package com.ilacad.exception;

public class InvalidIsbnException extends RuntimeException {

    public InvalidIsbnException(String isbn) {
        super(String.format("Invalid ISBN format for ISBN: %s", isbn));
    }
}
