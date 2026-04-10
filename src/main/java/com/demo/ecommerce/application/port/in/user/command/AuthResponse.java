package com.demo.ecommerce.application.port.in.user.command;

public record AuthResponse(String accessToken, String refreshToken ) {
}
