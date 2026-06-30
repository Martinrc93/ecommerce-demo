package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.model.user.vo.HashedPassword;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashedPasswordTest {

    @Test
    void shouldHashPasswordAndMatchRawValue() {
        HashedPassword hashedPassword = HashedPassword.of("password123");

        assertNotEquals("password123", hashedPassword.password());
        assertTrue(hashedPassword.password().startsWith("$2"));
        assertTrue(hashedPassword.matches("password123"));
        assertFalse(hashedPassword.matches("different123"));
    }

    @Test
    void shouldReuseExistingHash() {
        HashedPassword original = HashedPassword.of("password123");

        HashedPassword restored = HashedPassword.fromHashed(original.password());

        assertEquals(original.password(), restored.password());
        assertTrue(restored.matches("password123"));
    }

    @Test
    void shouldRejectShortPassword() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> HashedPassword.of("short")
        );

        assertEquals("Password must contain a minimum of 8 characters", exception.getMessage());
    }

    @Test
    void shouldRejectNullPassword() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> HashedPassword.of(null)
        );

        assertEquals("Password must contain a minimum of 8 characters", exception.getMessage());
    }
}
