package com.ilacad.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class InvalidInputException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(InvalidInputException.class);

    public InvalidInputException(Map<String, String> errors) {
        log.error("================= errors =================");
        errors.forEach((k, v) -> log.error("{} : {}", k, v));
    }
}
