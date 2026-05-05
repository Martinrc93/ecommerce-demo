package com.demo.ecommerce.infrastructure.input.web.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Petición para renovar el token o cerrar sesión")
public record RefreshRequest(
        @Schema(description = "Token de refresco válido asignado al usuario", example = "a1b2c3d4-5678-90ef-ghij-klmnopqrstuv")
        @NotBlank
        String refreshToken
) { }