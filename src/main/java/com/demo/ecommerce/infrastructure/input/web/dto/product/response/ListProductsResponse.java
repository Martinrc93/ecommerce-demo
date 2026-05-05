package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Respuesta que contiene una lista de productos")
public record ListProductsResponse(
        @Schema(description = "Lista de productos")
        List<ProductResponse> products
) {
}
