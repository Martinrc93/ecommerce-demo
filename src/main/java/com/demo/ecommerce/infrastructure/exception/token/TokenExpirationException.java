package com.demo.ecommerce.infrastructure.exception.token;

public class TokenExpirationException extends RuntimeException {
    public TokenExpirationException() {
        super("Toke expiated");
    }
}
