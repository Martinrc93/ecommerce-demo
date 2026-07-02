package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.model.product.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryTest {

    @Test
    void shouldCreateCategoryWithoutId() {
        Category category = Category.of("Tech");

        assertNull(category.id());
        assertEquals("Tech", category.name());
    }

    @Test
    void shouldCreateCategoryWithId() {
        Category category = Category.of(5L, "Tech");

        assertEquals(5L, category.id());
        assertEquals("Tech", category.name());
    }

    @Test
    void shouldRejectNullOrEmptyName() {
        InvalidValueObjectException nullException = assertThrows(
                InvalidValueObjectException.class,
                () -> Category.of(null)
        );
        assertEquals("Category name cannot be null or empty", nullException.getMessage());

        InvalidValueObjectException emptyException = assertThrows(
                InvalidValueObjectException.class,
                () -> Category.of("")
        );
        assertEquals("Category name cannot be null or empty", emptyException.getMessage());
    }

    @Test
    void shouldRejectNamesOutsideCurrentLengthBounds() {
        InvalidValueObjectException shortException = assertThrows(
                InvalidValueObjectException.class,
                () -> Category.of("Abc")
        );
        assertEquals("Category name must be between 3 and 30 characters", shortException.getMessage());

        InvalidValueObjectException longException = assertThrows(
                InvalidValueObjectException.class,
                () -> Category.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ1234")
        );
        assertEquals("Category name must be between 3 and 30 characters", longException.getMessage());
    }
}
