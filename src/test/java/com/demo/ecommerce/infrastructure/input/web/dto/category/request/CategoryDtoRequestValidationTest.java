package com.demo.ecommerce.infrastructure.input.web.dto.category.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryDtoRequestValidationTest {

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
        CategoryDtoRequest request = new CategoryDtoRequest("");

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAllowNullNameWithCurrentConstraints() {
        CategoryDtoRequest request = new CategoryDtoRequest(null);

        assertTrue(validator.validate(request).isEmpty());
    }
}
