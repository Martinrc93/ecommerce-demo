package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Datos del producto")
public record ProductResponse(
        @Schema(description = "Identificador único del producto", example = "1")
        Long id,

        @Schema(description = "Nombre comercial del producto", example = "Zapatillas Running")
        String name,

        @Schema(description = "Descripción detallada del producto", example = "Zapatillas ligeras con suela antideslizante")
        String description,

        @Schema(description = "Categoría principal del producto", example = "Calzado Deportivo")
        String category,

        @Schema(description = "Cantidad del producto", example = "2")
        Integer quantity,

        @Schema(description = "Precio unitario del producto", example = "129.99")
        BigDecimal price,

        @Schema(description = "Precio total (cantidad * precio unitario)", example = "259.98")
        BigDecimal total
) {
}
