package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record SaleDtoResponse(
        Long id,
        UUID userId,
        List<ItemsResponse> items,
        BigDecimal subTotal,
        BigDecimal discount,
        BigDecimal total) {
}
