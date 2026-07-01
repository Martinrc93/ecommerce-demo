package com.demo.ecommerce.infrastructure.input.web.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request used to refresh the token or log out")
public record RefreshRequest(
        @Schema(description = "Valid refresh token assigned to the user", example = "a1b2c3d4-5678-90ef-ghij-klmnopqrstuv")
        @NotBlank
        String refreshToken
) { }