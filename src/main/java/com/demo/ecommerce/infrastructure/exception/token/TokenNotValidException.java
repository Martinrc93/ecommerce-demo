package com.demo.ecommerce.infrastructure.exception.token;

public class TokenNotValidException extends RuntimeException {
    public TokenNotValidException() {
        super("Token not valid");
    }
}
