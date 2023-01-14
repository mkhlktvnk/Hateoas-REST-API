package com.example.blogapi.service.exception;

public class UnableToRemoveEntityException extends RuntimeException {
    public UnableToRemoveEntityException(String message) {
        super(message);
    }
}
