package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.auth.InvalidCredentialException;
import com.demo.ecommerce.domain.exception.auth.InvalidTokenException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthExceptionsTest {

    @Test
    void shouldExposeInvalidCredentialMessage() {
        InvalidCredentialException exception = new InvalidCredentialException();

        assertEquals("invalid credentials", exception.getMessage());
    }

    @Test
    void shouldExposeInvalidTokenMessage() {
        InvalidTokenException exception = new InvalidTokenException();

        assertEquals("invalid token", exception.getMessage());
    }
}
