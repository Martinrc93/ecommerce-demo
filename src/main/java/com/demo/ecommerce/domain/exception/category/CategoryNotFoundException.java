package com.demo.ecommerce.domain.exception.category;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super("Category not found: " + message);
    }
}
