package com.demo.ecommerce.domain.model.product;

public record Brand(Long id,String name) {

    public Brand{

        if ( name==null || name.isEmpty()){
            throw new IllegalArgumentException("El nombre de la marca no puede estar vacio");
        }
        if (name.length() <= 3 || name.length() >= 30){
            throw new IllegalArgumentException("El nombre de la marca debe tener entre 3 y 30 caracteres");
        }
    }

    public static Brand of(String name){
        return new Brand(null,name);
    }

    public static Brand of(Long id,String name){
        return new Brand(id, name);
    }
}
