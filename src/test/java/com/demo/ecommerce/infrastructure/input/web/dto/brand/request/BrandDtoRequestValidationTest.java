package com.demo.ecommerce.infrastructure.input.web.dto.brand.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BrandDtoRequestValidationTest {

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
    void shouldAllowBlankNameWithCurrentConstraints() {
        BrandDtoRequest request = new BrandDtoRequest("");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAllowNullNameWithCurrentConstraints() {
        BrandDtoRequest request = new BrandDtoRequest(null);

        assertTrue(validator.validate(request).isEmpty());
    }
}
