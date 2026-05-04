package com.demo.ecommerce.domain.exception.global;

public class NotFoundException extends DomainException{

    public NotFoundException(String message) {
        super(message);
    }
}
