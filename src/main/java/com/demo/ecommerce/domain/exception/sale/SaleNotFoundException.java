package com.demo.ecommerce.domain.exception.sale;

public class SaleNotFoundException extends RuntimeException {
    public SaleNotFoundException(String message) {
        super("Sale not found: " + message);
    }
}
