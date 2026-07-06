package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRequestValidationTest {

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
    void shouldAcceptValidCreateProductRequest() {
        CreateProductRequest request = new CreateProductRequest(
                "Running Shoes",
                "Lightweight running shoes",
                "Nike",
                "Sports Footwear",
                new BigDecimal("129.99"),
                100,
                true
        );

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldRejectInvalidCreateProductRequest() {
        CreateProductRequest request = new CreateProductRequest(
                "",
                "short",
                "",
                "",
                BigDecimal.ZERO,
                0,
                false
        );

        assertEquals(8, validator.validate(request).size());
    }

    @Test
    void shouldAcceptValidUpdateProductDetailRequest() {
        UpdateProductDetailRequest request = new UpdateProductDetailRequest(
                "Running Shoes",
                "Lightweight running shoes",
                "Nike",
                "Sports Footwear"
        );

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldRejectInvalidUpdateProductDetailRequest() {
        UpdateProductDetailRequest request = new UpdateProductDetailRequest(
                "",
                "short",
                "",
                ""
        );

        assertEquals(6, validator.validate(request).size());
    }

    @Test
    void shouldAllowInvalidValuesInUpdateProductRequestWithCurrentConstraints() {
        UpdateProductRequest request = new UpdateProductRequest(
                "",
                "",
                "",
                "",
                0,
                BigDecimal.ZERO,
                false
        );

        assertTrue(validator.validate(request).isEmpty());
    }
}
