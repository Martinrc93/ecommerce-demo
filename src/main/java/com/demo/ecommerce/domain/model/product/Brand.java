package com.demo.ecommerce.domain.model.product;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

public record Brand(Long id, String name) {

    public Brand{

        if ( name==null || name.isEmpty()){
            throw new InvalidValueObjectException("brand name cannot be null or empty");
        }
        if (name.length() <= 3 || name.length() >= 30){
            throw new InvalidValueObjectException("brand name must be between 3 and 30 characters");
        }
    }

    public static Brand of(String name){
        return new Brand(null,name);
    }

    public static Brand of(Long id,String name){
        return new Brand(id, name);
    }

}
