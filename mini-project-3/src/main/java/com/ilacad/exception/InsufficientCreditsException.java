package com.ilacad.exception;

import java.math.BigDecimal;

public class InsufficientCreditsException extends RuntimeException {

    public InsufficientCreditsException(BigDecimal credits) {
        super(String.format("Insufficient credits: %s", credits));
    }
}
