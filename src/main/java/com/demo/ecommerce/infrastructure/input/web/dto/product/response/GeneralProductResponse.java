package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "General product data")
public record GeneralProductResponse(
        @Schema(description = "Unique product identifier", example = "1")
        Long id,

        @Schema(description = "Product display name", example = "Running Shoes")
        String name,

        @Schema(description = "Detailed product description", example = "Lightweight running shoes with a non-slip sole")
        String description,

        @Schema(description = "Product brand", example = "Nike")
        String brand,

        @Schema(description = "Primary product category", example = "Sports Footwear")
        String category,

        @Schema(description = "Current inventory quantity", example = "100")
        Integer stock,

        @Schema(description = "Unit product price", example = "129.99")
        BigDecimal price,

        @Schema(description = "Indicates whether the product is visible and available for purchase", example = "true")
        boolean active
) { }
