package com.demo.ecommerce.infrastructure.input.web.dto.product.response;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ProductResponseRecordTest {

    @Test
    void shouldExposeGeneralProductResponseFields() {
        GeneralProductResponse response = new GeneralProductResponse(
                1L,
                "Running Shoes",
                "Lightweight running shoes",
                "Nike",
                "Sports Footwear",
                100,
                new BigDecimal("129.99"),
                true
        );

        assertEquals(1L, response.id());
        assertEquals("Running Shoes", response.name());
        assertEquals("Lightweight running shoes", response.description());
        assertEquals("Nike", response.brand());
        assertEquals("Sports Footwear", response.category());
        assertEquals(100, response.stock());
        assertEquals(new BigDecimal("129.99"), response.price());
        assertEquals(true, response.active());
    }

    @Test
    void shouldExposeProductResponseFields() {
        ProductResponse response = new ProductResponse(
                1L,
                "Running Shoes",
                "Lightweight running shoes",
                "Sports Footwear",
                2,
                new BigDecimal("129.99"),
                new BigDecimal("259.98")
        );

        assertEquals(1L, response.id());
        assertEquals("Running Shoes", response.name());
        assertEquals("Lightweight running shoes", response.description());
        assertEquals("Sports Footwear", response.category());
        assertEquals(2, response.quantity());
        assertEquals(new BigDecimal("129.99"), response.price());
        assertEquals(new BigDecimal("259.98"), response.total());
    }

    @Test
    void shouldExposeListProductsResponseFields() {
        ProductResponse item = new ProductResponse(1L, "Running Shoes", "Lightweight running shoes", "Sports Footwear", 2, new BigDecimal("129.99"), new BigDecimal("259.98"));
        List<ProductResponse> products = List.of(item);
        ListProductsResponse response = new ListProductsResponse(products);

        assertSame(products, response.products());
        assertEquals(1, response.products().size());
        assertSame(item, response.products().getFirst());
    }
}
