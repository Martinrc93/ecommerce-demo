package com.demo.ecommerce.domain.exception.auth;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException() {
        super("invalid credentials");
    }

}
