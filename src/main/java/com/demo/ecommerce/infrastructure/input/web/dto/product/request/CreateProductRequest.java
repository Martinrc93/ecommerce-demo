package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import java.math.BigDecimal;

public record CreateProductRequest(

        String name,
        String description,
        String brand,
        String category,
        BigDecimal price,
        Integer stock,
        boolean active

) { }