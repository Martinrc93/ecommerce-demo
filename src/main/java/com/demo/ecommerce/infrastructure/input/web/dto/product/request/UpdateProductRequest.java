package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

public record UpdateProductRequest(
        String name,
        String description,
        String brand,
        String category,
        Integer stock,
        Double price
) {
}
