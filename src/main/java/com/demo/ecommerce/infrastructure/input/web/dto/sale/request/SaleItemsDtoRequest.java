package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import java.math.BigDecimal;

public record SaleItemsDtoRequest(
        Long productId,
        Integer quantity) {

}
