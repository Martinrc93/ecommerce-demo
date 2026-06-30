package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.model.user.vo.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void shouldLowercaseAcceptedEmail() {
        Email email = Email.of("John.Doe+SHOP@example.com");

        assertEquals("john.doe+shop@example.com", email.email());
    }

    @Test
    void shouldRejectNullEmail() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> Email.of(null)
        );

        assertEquals("Email invalid: null", exception.getMessage());
    }

    @Test
    void shouldRejectEmailWithoutTopLevelDomain() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> Email.of("john@example")
        );

        assertEquals("Email invalid: john@example", exception.getMessage());
    }

    @Test
    void shouldRejectSubdomainEmailsUnderCurrentRegex() {
        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> Email.of("john@shop.example.com")
        );

        assertEquals("Email invalid: john@shop.example.com", exception.getMessage());
    }
}
