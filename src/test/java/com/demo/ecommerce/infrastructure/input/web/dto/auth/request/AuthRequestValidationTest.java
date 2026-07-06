package com.demo.ecommerce.infrastructure.input.web.dto.auth.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthRequestValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    @Test
    void shouldAcceptValidLoginRequest() {
        LoginRequest request = new LoginRequest("user@example.com", "StrongPass1!");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAllowNullEmailWithCurrentConstraints() {
        LoginRequest request = new LoginRequest(null, "StrongPass1!");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldRejectWeakPassword() {
        LoginRequest request = new LoginRequest("user@example.com", "weak");

        assertEquals(2, validator.validate(request).size());
    }

    @Test
    void shouldRejectBlankRefreshToken() {
        RefreshRequest request = new RefreshRequest("   ");

        assertEquals(1, validator.validate(request).size());
    }
}
