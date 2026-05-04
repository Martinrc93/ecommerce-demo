package com.demo.ecommerce.domain.exception.auth;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("invalid token");
    }
}
