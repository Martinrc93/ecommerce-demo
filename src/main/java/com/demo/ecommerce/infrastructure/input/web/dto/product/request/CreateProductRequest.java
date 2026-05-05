package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Datos para crear un nuevo producto")
public record CreateProductRequest(

        @Schema(description = "Nombre comercial del producto", example = "Zapatillas Running")
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
        String name,

        @Schema(description = "Descripción detallada del producto", example = "Zapatillas ligeras con suela antideslizante")
        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters long")
        String description,

        @Schema(description = "Marca del producto", example = "Nike")
        @NotBlank(message = "Brand is required")
        @Size(min= 3, max = 50, message = "Brand must be between 3 and 50 characters long")
        String brand,

        @Schema(description = "Categoría principal del producto", example = "Calzado Deportivo")
        @NotBlank(message = "Category is required")
        String category,

        @Schema(description = "Precio unitario del producto", example = "129.99")
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be a positive number")
        BigDecimal price,

        @Schema(description = "Cantidad inicial de inventario disponible", example = "100")
        @NotNull(message = "Stock is required")
        @Positive(message = "Stock must be a positive number")
        Integer stock,

        @Schema(description = "Indica si el producto está visible y disponible para compra", example = "true")
        @NotNull(message = "Active is required")
        boolean active

) { }