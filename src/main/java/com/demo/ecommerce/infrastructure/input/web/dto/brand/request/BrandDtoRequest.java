package com.demo.ecommerce.infrastructure.input.web.dto.brand.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data used to create or update a brand")
public record BrandDtoRequest(
        @Schema(description = "Brand name", example = "Nike")
        String name
) {
}
