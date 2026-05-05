package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "Respuesta detallada de una venta")
public record SaleDtoResponse(

        @Schema(description = "ID autogenerado de la venta", example = "1")
        Long id,

        @Schema(description = "Identificador del usuario que realizó la compra", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        UUID userId,

        @Schema(description = "Lista de productos comprados")
        List<ItemsResponse> items,

        @Schema(description = "Subtotal de la venta sin aplicar descuentos", example = "299.99")
        BigDecimal subTotal,

        @Schema(description = "Descuento total aplicado a la venta", example = "10.00")
        BigDecimal discount,

        @Schema(description = "Monto final a pagar", example = "269.99")
        BigDecimal total) {
}