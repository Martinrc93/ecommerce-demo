package com.demo.ecommerce.application.port.in.product.command;

import java.math.BigDecimal;

public record CreateProductCommand(

        String name,
        String description,
        String brand,
        String category,
        BigDecimal price,
        Integer stock,
        boolean active

) { }