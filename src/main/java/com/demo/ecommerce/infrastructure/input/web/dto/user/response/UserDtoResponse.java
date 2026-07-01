package com.demo.ecommerce.infrastructure.input.web.dto.user.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User response data")
public record UserDtoResponse(
        @Schema(description = "User first name", example = "John")
        String name,

        @Schema(description = "User last name", example = "Perez")
        String lastName,

        @Schema(description = "User email address", example = "john.perez@example.com")
        String email
) {
}
