package com.demo.ecommerce.application.port.in.sale.command;

public record Item(
        Long productId,
        Integer quantity,
        Integer reduceStock)
{
}
