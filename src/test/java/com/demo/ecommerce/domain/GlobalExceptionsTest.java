package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.DomainException;
import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class GlobalExceptionsTest {

    @Test
    void shouldExposeInvalidValueObjectMessage() {
        InvalidValueObjectException exception = new InvalidValueObjectException("invalid value");

        assertEquals("invalid value", exception.getMessage());
        assertInstanceOf(DomainException.class, exception);
    }

    @Test
    void shouldExposeNotFoundMessage() {
        NotFoundException exception = new NotFoundException("missing");

        assertEquals("missing", exception.getMessage());
        assertInstanceOf(DomainException.class, exception);
    }
}
