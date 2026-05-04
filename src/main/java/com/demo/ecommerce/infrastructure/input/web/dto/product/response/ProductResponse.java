package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String category,
        Integer quantity,
        BigDecimal price,
        BigDecimal total
) {
}
