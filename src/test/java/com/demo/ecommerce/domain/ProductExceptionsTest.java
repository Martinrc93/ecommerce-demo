package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import com.demo.ecommerce.domain.exception.product.ProductNameNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ProductExceptionsTest {

    @Test
    void shouldExposeProductIdNotFoundMessage() {
        ProductIdNotFoundException exception = new ProductIdNotFoundException(42L);

        assertEquals("Product not found with id: 42", exception.getMessage());
        assertInstanceOf(NotFoundException.class, exception);
    }

    @Test
    void shouldExposeProductNameNotFoundMessage() {
        ProductNameNotFoundException exception = new ProductNameNotFoundException("Missing product");

        assertEquals("Missing product", exception.getMessage());
    }
}
