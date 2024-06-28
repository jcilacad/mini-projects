package com.ilacad.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(Long id) {
        super(String.format("Cart not found with id: %s", id));
    }
}
