package com.demo.ecommerce.domain.model.user.vo;

public record UserName(String name,String lastName) {

    public UserName{
        if (name == null || name.isBlank() ) throw new RuntimeException(""); //TODO personalizar excepcion
        if (lastName == null || lastName.isBlank() ) throw new RuntimeException(""); //TODO personalizar excepcion

        name = name.trim();
        lastName = lastName.trim();
    }

    public String fullName(){
        return name + " " + lastName;
    }
}
