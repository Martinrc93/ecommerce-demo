package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product details to include in the sale")
public record SaleItemsDtoRequest(
        
        @Schema(description = "Product ID", example = "1")
        Long productId,
        
        @Schema(description = "Quantity of units to purchase", example = "2")
        Integer quantity) {

}