package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
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
    void shouldThrowExceptionWhenStockIsNegative() {
        InvalidValueObjectException exception = assertThrows(InvalidValueObjectException.class, () -> Stock.of(-5));
        assertEquals("Amount must be greater than or equal to 0", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStockIsNull() {
        InvalidValueObjectException exception = assertThrows(InvalidValueObjectException.class, () -> Stock.of(null));
        assertEquals("Amount must be greater than or equal to 0", exception.getMessage());
    }

    @Test
    void shouldUpdateStockCorrectly() {
        Stock stock = Stock.of(10);
        Stock updated = stock.updateStock(3);
        assertEquals(7, updated.stock());
    }

    @Test
    void shouldThrowExceptionWhenStockDiscountIsZero() {
        Stock stock = Stock.of(10);

        InvalidValueObjectException exception = assertThrows(InvalidValueObjectException.class, () -> stock.updateStock(0));

        assertEquals("Stock to discount must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenStockDiscountIsNull() {
        Stock stock = Stock.of(10);

        InvalidValueObjectException exception = assertThrows(InvalidValueObjectException.class, () -> stock.updateStock(null));

        assertEquals("Stock to discount must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDiscountExceedsStock() {
        Stock stock = Stock.of(5);

        InvalidValueObjectException exception = assertThrows(InvalidValueObjectException.class, () -> stock.updateStock(10));

        assertEquals("Insufficient stock available", exception.getMessage());
    }
}
