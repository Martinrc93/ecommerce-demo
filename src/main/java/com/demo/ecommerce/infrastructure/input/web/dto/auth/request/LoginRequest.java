package com.demo.ecommerce.infrastructure.input.web.dto.auth.request;

public record LoginRequest(
        //@NotBlank
        //@Email
        String email,

        //@NotBlank
        //size(min = 8)
        String password
) {
}
