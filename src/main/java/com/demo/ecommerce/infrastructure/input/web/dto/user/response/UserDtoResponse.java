package com.demo.ecommerce.infrastructure.input.web.dto.user.response;

public record UserDtoResponse(
        String name,
        String lastName,
        String email
) {
}
