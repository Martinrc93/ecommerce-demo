package com.demo.ecommerce.infrastructure.input.web.dto.brand.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de respuesta de la marca")
public record BrandDtoResponse(
        @Schema(description = "Nombre de la marca", example = "Nike")
        String name
) {
}
