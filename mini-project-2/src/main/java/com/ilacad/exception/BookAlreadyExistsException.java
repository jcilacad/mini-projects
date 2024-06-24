package com.ilacad.exception;

public class BookAlreadyExistsException extends RuntimeException {

    public BookAlreadyExistsException(String isbn) {
        super(String.format("Book already exists with isbn: %s", isbn));
    }
}
