package com.demo.ecommerce.domain.exception.global;

public abstract class DomainException extends RuntimeException {

    protected DomainException(String message){
        super(message);
    }
}
