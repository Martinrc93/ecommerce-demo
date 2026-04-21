package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import java.math.BigDecimal;

public record ItemsResponse(
        String name,
        String description,
        String category,
        Integer quantity,
        BigDecimal price,
        BigDecimal discount
) {
}
