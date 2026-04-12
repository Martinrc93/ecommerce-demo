package com.demo.ecommerce.application.port.in.user.usecase.dto;

public record RegisterResult(
        String firstName,
        String lastName,
        String accessToken,
        String refreshToken
) {
}
