package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import java.util.List;
import java.util.UUID;

public record CreateSaleDtoRequest(
        UUID userId,
        String Discount,
        List<SaleItemsDtoRequest> items)
{
}
