package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Details of each sold product")
public record ItemsResponse(

        @Schema(description = "Product name", example = "Running Shoes", nullable = true)
        String name,

        @Schema(description = "", example = "Nike", nullable = true)
        String brand,

        @Schema(description = "Category the product belongs to", example = "Footwear", nullable = true)
        String category,

        @Schema(description = "Purchased quantity", example = "2")
        Integer quantity,

        @Schema(description = "Unit product price", example = "149.99")
        BigDecimal price,

        @Schema(description = "Discount applied to the product", example = "0.00")
        BigDecimal discount
) {
}