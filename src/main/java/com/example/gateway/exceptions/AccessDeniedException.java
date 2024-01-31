package com.example.gateway.exceptions;

/**
 * Custom exception class representing access denied scenario.
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
