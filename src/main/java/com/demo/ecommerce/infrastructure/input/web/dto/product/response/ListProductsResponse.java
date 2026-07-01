package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response containing a product list")
public record ListProductsResponse(
        @Schema(description = "Product list")
        List<ProductResponse> products
) {
}
