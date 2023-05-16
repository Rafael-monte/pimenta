package com.example.sisave.exceptions;

public class BadRequestBodyException extends RuntimeException {
    public BadRequestBodyException(String message) {
        super(message);
    }
}
