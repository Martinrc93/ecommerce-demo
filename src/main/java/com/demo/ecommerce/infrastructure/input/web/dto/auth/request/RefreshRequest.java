package com.demo.ecommerce.infrastructure.input.web.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank
        String refreshToken
) { }