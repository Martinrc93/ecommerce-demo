package com.demo.ecommerce.domain.model.product;

public record ProductDetail(String name, String description, String brand, String category) {

    public static ProductDetail of(String name, String description, String brand, String category){
        return new ProductDetail(name, description, brand, category);
    }
}
