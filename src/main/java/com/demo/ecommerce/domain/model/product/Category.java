package com.demo.ecommerce.domain.model.product;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

public record Category(Long id, String name) {

    public Category{

        if ( name==null || name.isEmpty()){
            throw new InvalidValueObjectException("Category name cannot be null or empty");
        }

        if (name.length() <= 3 || name.length() >= 30){
            throw new InvalidValueObjectException("Category name must be between 3 and 30 characters");
        }

    }

    public static Category of(String name){
        return new Category(null,name);
    }

    public static Category of(Long id, String name){
        return new Category(id,name);
    }
}
