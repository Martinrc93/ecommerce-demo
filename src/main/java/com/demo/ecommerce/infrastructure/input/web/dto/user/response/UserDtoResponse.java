package com.demo.ecommerce.infrastructure.input.web.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos de respuesta del usuario")
public record UserDtoResponse(
        @Schema(description = "Nombre del usuario", example = "Juan")
        String name,

        @Schema(description = "Apellido del usuario", example = "Pérez")
        String lastName,

        @Schema(description = "Correo electrónico del usuario", example = "juan.perez@example.com")
        String email
) {
}
