package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.product.Stock;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void shouldCreateStockSuccessfully() {
        Stock stock = Stock.of(10);
        assertEquals(10, stock.stock());
    }

    @Test
    void shouldThrowExceptionWhenStockIsZero() {
        assertThrows(IllegalArgumentException.class, () -> Stock.of(0));
    }

    @Test
    void shouldThrowExceptionWhenStockIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> Stock.of(-5));
    }

    @Test
    void shouldThrowExceptionWhenStockIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Stock.of(null));
    }

    @Test
    void shouldUpdateStockCorrectly() {
        Stock stock = Stock.of(10);
        Stock updated = stock.updateStock(3);
        assertEquals(7, updated.stock());
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsZero() {
        Stock stock = Stock.of(10);
        assertThrows(IllegalArgumentException.class, () -> stock.updateStock(0));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsNull() {
        Stock stock = Stock.of(10);
        assertThrows(IllegalArgumentException.class, () -> stock.updateStock(null));
    }

    @Test
    void shouldThrowExceptionWhenDiscountExceedsStock() {
        Stock stock = Stock.of(5);
        assertThrows(IllegalArgumentException.class, () -> stock.updateStock(10));
    }
}
