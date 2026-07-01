package com.demo.ecommerce.infrastructure.input.web.dto.sale.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.UUID;

@Schema(description = "Data used to create a new sale")
public record CreateSaleDtoRequest(

        @Schema(description = "Unique identifier of the user making the purchase", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
        UUID userId,

        @Schema(description = "Global discount applied to the sale total (e.g. '10.00' for 10%)", example = "10.00")
        String Discount,

        @Schema(description = "List of products included in the sale")
        List<SaleItemsDtoRequest> items)
{
}
