package com.demo.ecommerce.domain.model.user.vo;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

public record UserName(String name, String lastName) {

    public UserName{
        if (name == null || name.isBlank() ) throw new InvalidValueObjectException("Name cannot be null or empty");
        if (lastName == null || lastName.isBlank() ) throw new InvalidValueObjectException("lastname cannot be null or empty");

        name = name.trim();
        lastName = lastName.trim();
    }

    public String fullName(){
        return name + " " + lastName;
    }
}
