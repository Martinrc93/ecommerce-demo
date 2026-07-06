package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleRequestValidationTest {

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
    void shouldAllowMalformedCreateSaleRequestWithCurrentConstraints() {
        CreateSaleDtoRequest request = new CreateSaleDtoRequest(
                null,
                "not-a-number",
                List.of(new SaleItemsDtoRequest(null, 0))
        );

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAllowMalformedSaleItemWithCurrentConstraints() {
        SaleItemsDtoRequest request = new SaleItemsDtoRequest(null, -1);

        assertTrue(validator.validate(request).isEmpty());
    }
}
