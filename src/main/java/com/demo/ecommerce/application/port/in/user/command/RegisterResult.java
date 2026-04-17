package com.demo.ecommerce.application.port.in.user.command;

public record RegisterResult(
        String firstName,
        String lastName,
        String accessToken,
        String refreshToken
) {
}
