package com.demo.ecommerce.application.port.in.product.command;

public record UpdateProductDetailCommand(

        String name,
        String description,
        String brand,
        String category

) { }