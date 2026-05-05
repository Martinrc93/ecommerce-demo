package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos para actualizar los detalles de un producto")
public record UpdateProductDetailRequest(

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
        String category

) { }
