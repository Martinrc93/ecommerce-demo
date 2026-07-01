package com.demo.ecommerce.infrastructure.input.web.dto.brand.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Brand response data")
public record BrandDtoResponse(
        @Schema(description = "Brand name", example = "Nike")
        String name
) {
}
