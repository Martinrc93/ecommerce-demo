package com.demo.ecommerce.infrastructure.input.web.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Datos requeridos para iniciar sesión")
public record LoginRequest(

        @Schema(description = "Correo electrónico del usuario registrado", example = "usuario@example.com")
        @Email(message = "Invalid email format")
        String email,

        @Schema(description = "Contraseña segura del usuario", example = "PasswordSegura123!")
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
        )
        String password
) {
}