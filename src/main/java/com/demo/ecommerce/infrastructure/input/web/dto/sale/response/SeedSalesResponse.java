package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Summary of the generated ecommerce seed")
public record SeedSalesResponse(

        @Schema(description = "Number of categories created", example = "8")
        int categories,

        @Schema(description = "Number of brands created", example = "12")
        int brands,

        @Schema(description = "Number of products created", example = "40")
        int products,

        @Schema(description = "Number of users created", example = "10")
        int users,

        @Schema(description = "Actual number of seeded sales", example = "12")
        int createdSales,

        @Schema(description = "Start of the generated sales window", example = "2026-07-06T10:15:30")
        String windowStart,

        @Schema(description = "End of the generated sales window", example = "2026-07-06T17:15:30")
        String windowEnd
) {
}
