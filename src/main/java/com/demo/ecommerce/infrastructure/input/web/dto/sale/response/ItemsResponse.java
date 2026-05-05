package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Detalle de cada producto vendido")
public record ItemsResponse(

        @Schema(description = "Nombre del producto", example = "Zapatillas Running", nullable = true)
        String name,

        @Schema(description = "Descripción del producto", example = "Zapatillas ligeras para correr", nullable = true)
        String description,

        @Schema(description = "Categoría a la que pertenece el producto", example = "Calzado", nullable = true)
        String category,

        @Schema(description = "Cantidad comprada", example = "2")
        Integer quantity,

        @Schema(description = "Precio unitario del producto", example = "149.99")
        BigDecimal price,

        @Schema(description = "Descuento aplicado al producto", example = "0.00")
        BigDecimal discount
) {
}