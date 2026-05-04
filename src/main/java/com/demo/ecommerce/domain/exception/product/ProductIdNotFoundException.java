package com.demo.ecommerce.domain.exception.product;

import com.demo.ecommerce.domain.exception.global.NotFoundException;


public class ProductIdNotFoundException extends NotFoundException {
    public ProductIdNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
