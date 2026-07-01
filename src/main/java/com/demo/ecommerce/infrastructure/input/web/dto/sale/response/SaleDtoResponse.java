package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Detailed sale response")
public record SaleDtoResponse(

        @Schema(description = "Auto-generated sale ID", example = "1")
        Long id,

        @Schema(description = "Identifier of the user who made the purchase", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        UUID userId,

        @Schema(description = "List of purchased products")
        List<ItemsResponse> items,

        @Schema(description = "Sale subtotal before discounts", example = "299.99")
        BigDecimal subTotal,

        @Schema(description = "Total discount applied to the sale", example = "10.00")
        BigDecimal discount,

        @Schema(description = "Monto final a pagar", example = "269.99")
        BigDecimal total) {
}