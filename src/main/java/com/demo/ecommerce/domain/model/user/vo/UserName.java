package com.demo.ecommerce.domain.model.user.vo;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

public record UserName(String name, String lastName) {

    public UserName{
        if (name == null || name.isBlank() ) throw new InvalidValueObjectException("Name cannot be null or empty");
        if (lastName == null || lastName.isBlank() ) throw new InvalidValueObjectException("lastname cannot be null or empty");

        if (verifyCharacter(name)) throw new InvalidValueObjectException("Name cannot contain numbers or special characters");
        if (verifyCharacter(lastName)) throw new InvalidValueObjectException("Lastname cannot contain numbers or special characters");

        name = name.trim();
        lastName = lastName.trim();
    }

    public String fullName(){
        return name + " " + lastName;
    }

    private boolean verifyCharacter(String name){

        boolean containNum = name.chars().anyMatch(Character::isDigit);
        boolean containSpecialChar = name.chars().anyMatch(c -> !Character.isLetterOrDigit(c));

        return containNum || containSpecialChar;
    }

}
