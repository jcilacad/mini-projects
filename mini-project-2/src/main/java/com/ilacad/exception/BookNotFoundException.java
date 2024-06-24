package com.ilacad.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String field, String value) {
        super(String.format("Book not found with %s : %s", field, value));
    }
}
