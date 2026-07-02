package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryExceptionsTest {

    @Test
    void shouldExposeCategoryNotFoundMessage() {
        CategoryNotFoundException exception = new CategoryNotFoundException("Tech");

        assertEquals("Category not found: Tech", exception.getMessage());
    }
}
