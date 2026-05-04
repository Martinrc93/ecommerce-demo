package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import com.demo.ecommerce.infrastructure.input.web.dto.product.response.ProductResponse;

import java.util.List;

public record ListProductsResponse(
        List<ProductResponse> products
) {
}
