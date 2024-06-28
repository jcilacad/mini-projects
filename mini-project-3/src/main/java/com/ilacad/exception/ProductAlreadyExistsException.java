package com.ilacad.exception;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String name) {
        super(String.format("Product already exists with name: %s", name));
    }
}
