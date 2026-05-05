package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para actualizar parcialmente un producto")
public record UpdateProductRequest(

        @Schema(description = "Nombre comercial del producto", example = "Zapatillas Running")
        String name,

        @Schema(description = "Descripción detallada del producto", example = "Zapatillas ligeras con suela antideslizante")
        String description,

        @Schema(description = "Marca del producto", example = "Nike")
        String brand,

        @Schema(description = "Categoría principal del producto", example = "Calzado Deportivo")
        String category,

        @Schema(description = "Cantidad de inventario disponible", example = "100")
        Integer stock,

        @Schema(description = "Precio unitario del producto", example = "129.99")
        Double price,

        @Schema(description = "Indica si el producto está visible y disponible para compra", example = "true")
        boolean active

) {
}
