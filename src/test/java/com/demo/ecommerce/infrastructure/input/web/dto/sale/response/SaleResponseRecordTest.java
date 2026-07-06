package com.demo.ecommerce.infrastructure.input.web.dto.sale.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SaleResponseRecordTest {

    @Test
    void shouldExposeItemsResponseFields() {
        ItemsResponse response = new ItemsResponse(
                "Running Shoes",
                "Nike",
                "Footwear",
                2,
                new BigDecimal("149.99"),
                BigDecimal.ZERO
        );

        assertEquals("Running Shoes", response.name());
        assertEquals("Nike", response.brand());
        assertEquals("Footwear", response.category());
        assertEquals(2, response.quantity());
        assertEquals(new BigDecimal("149.99"), response.price());
        assertEquals(BigDecimal.ZERO, response.discount());
    }

    @Test
    void shouldExposeSaleDtoResponseFields() {
        UUID userId = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
        ItemsResponse item = new ItemsResponse("Running Shoes", "Nike", "Footwear", 2, new BigDecimal("149.99"), BigDecimal.ZERO);
        List<ItemsResponse> items = List.of(item);
        SaleDtoResponse response = new SaleDtoResponse(
                1L,
                userId,
                items,
                new BigDecimal("299.98"),
                new BigDecimal("10.00"),
                new BigDecimal("289.98")
        );

        assertEquals(1L, response.id());
        assertEquals(userId, response.userId());
        assertSame(items, response.items());
        assertEquals(new BigDecimal("299.98"), response.subTotal());
        assertEquals(new BigDecimal("10.00"), response.discount());
        assertEquals(new BigDecimal("289.98"), response.total());
    }
}
