package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Product data")
public record ProductResponse(
        @Schema(description = "Unique product identifier", example = "1")
        Long id,

        @Schema(description = "Product display name", example = "Running Shoes")
        String name,

        @Schema(description = "Detailed product description", example = "Lightweight running shoes with a non-slip sole")
        String description,

        @Schema(description = "Primary product category", example = "Sports Footwear")
        String category,

        @Schema(description = "Product quantity", example = "2")
        Integer quantity,

        @Schema(description = "Unit product price", example = "129.99")
        BigDecimal price,

        @Schema(description = "Total price (quantity * unit price)", example = "259.98")
        BigDecimal total
) {
}
