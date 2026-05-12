package com.demo.ecommerce.domain.model.product;

public record Category(Long id, String name) {

    public Category{

        if ( name==null || name.isEmpty()){
            throw new IllegalArgumentException("El nombre de la categoria no puede estar vacio");
        }

        if (name.length() <= 3 || name.length() >= 30){
            throw new IllegalArgumentException("El nombre de la categoria debe tener entre 3 y 30 caracteres");
        }

    }

    public static Category of(String name){
        return new Category(null,name);
    }

    public static Category of(Long id, String name){
        return new Category(id,name);
    }
}
