package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.DomainException;
import com.demo.ecommerce.domain.exception.sale.SaleNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class SaleExceptionsTest {

    @Test
    void shouldExposeSaleNotFoundMessage() {
        SaleNotFoundException exception = new SaleNotFoundException("123");

        assertEquals("Sale not found: 123", exception.getMessage());
        assertInstanceOf(DomainException.class, exception);
    }
}
