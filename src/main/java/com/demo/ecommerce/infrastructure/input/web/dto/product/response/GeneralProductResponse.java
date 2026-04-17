package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import java.math.BigDecimal;

public record GeneralProductResponse(
        Long id,
        String name,
        String description,
        String brand,
        String category,
        Integer stock,
        BigDecimal price,
        boolean active
) { }