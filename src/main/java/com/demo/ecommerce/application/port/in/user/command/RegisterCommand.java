package com.demo.ecommerce.application.port.in.user.command;

public record RegisterCommand(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
