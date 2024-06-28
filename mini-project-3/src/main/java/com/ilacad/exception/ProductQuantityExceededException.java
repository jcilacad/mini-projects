package com.ilacad.exception;

public class ProductQuantityExceededException extends RuntimeException {

    public ProductQuantityExceededException(String message) {
        super(message);
    }
}
