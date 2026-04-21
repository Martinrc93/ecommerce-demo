package com.demo.ecommerce.application.port.in.product.command;

import java.math.BigDecimal;

public record UpdateProductCommand(
        String name,
        String description,
        String brand,
        String category,
        Integer stock,
        BigDecimal price,
        boolean active
) {
}
