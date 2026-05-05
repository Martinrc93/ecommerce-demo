package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalles del producto a incluir en la venta")
public record SaleItemsDtoRequest(
        
        @Schema(description = "ID del producto", example = "1")
        Long productId,
        
        @Schema(description = "Cantidad de unidades a comprar", example = "2")
        Integer quantity) {

}