package com.demo.ecommerce.domain.exception.sale;

import com.demo.ecommerce.domain.exception.global.DomainException;

public class SaleNotFoundException extends DomainException {
    public SaleNotFoundException(String message) {
        super("Sale not found: " + message);
    }
}
