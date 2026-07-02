package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.model.product.Brand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrandTest {

    @Test
    void shouldCreateBrandWithoutId() {
        Brand brand = Brand.of("Lenovo");

        assertNull(brand.id());
        assertEquals("Lenovo", brand.name());
    }

    @Test
    void shouldCreateBrandWithId() {
        Brand brand = Brand.of(7L, "Lenovo");

        assertEquals(7L, brand.id());
        assertEquals("Lenovo", brand.name());
    }

    @Test
    void shouldRejectNullOrEmptyName() {
        InvalidValueObjectException nullException = assertThrows(
                InvalidValueObjectException.class,
                () -> Brand.of(null)
        );
        assertEquals("brand name cannot be null or empty", nullException.getMessage());

        InvalidValueObjectException emptyException = assertThrows(
                InvalidValueObjectException.class,
                () -> Brand.of("")
        );
        assertEquals("brand name cannot be null or empty", emptyException.getMessage());
    }

    @Test
    void shouldRejectNamesOutsideCurrentLengthBounds() {
        InvalidValueObjectException shortException = assertThrows(
                InvalidValueObjectException.class,
                () -> Brand.of("Abc")
        );
        assertEquals("brand name must be between 3 and 30 characters", shortException.getMessage());

        InvalidValueObjectException longException = assertThrows(
                InvalidValueObjectException.class,
                () -> Brand.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234")
        );
        assertEquals("brand name must be between 3 and 30 characters", longException.getMessage());
    }
}
