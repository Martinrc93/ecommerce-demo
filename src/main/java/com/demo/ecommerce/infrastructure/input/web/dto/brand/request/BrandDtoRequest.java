package com.demo.ecommerce.infrastructure.input.web.dto.brand.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos para crear o actualizar una marca")
public record BrandDtoRequest(
        @Schema(description = "Nombre de la marca", example = "Nike")
        String name
) {
}
