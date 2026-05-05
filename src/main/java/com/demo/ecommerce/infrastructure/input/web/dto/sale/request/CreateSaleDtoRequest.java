package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Datos para crear una nueva venta")
public record CreateSaleDtoRequest(

        @Schema(description = "Identificador único del usuario que realiza la compra", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        UUID userId,

        @Schema(description = "Descuento general a aplicar sobre el total de la venta (ej. '10.00' para 10%)", example = "10.00")
        String Discount,

        @Schema(description = "Lista de productos que componen la venta")
        List<SaleItemsDtoRequest> items)
{
}