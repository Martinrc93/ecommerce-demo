package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

public record UpdateProductDetailRequest(

        String name,
        String description,
        String brand,
        String category

) { }