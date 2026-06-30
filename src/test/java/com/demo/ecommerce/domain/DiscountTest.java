package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.shared.vo.Discount;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DiscountTest {

    @Test
    void shouldCreateDiscountSuccessfully() {
        Discount discount = Discount.of(new BigDecimal("10"));
        assertEquals(new BigDecimal("0.10"), discount.discount());
    }

    @Test
    void shouldCreateDiscountWith50Percent() {
        Discount discount = Discount.of(new BigDecimal("50"));
        assertEquals(new BigDecimal("0.50"), discount.discount());
    }

    @Test
    void shouldCreateDiscountWithMinimumValidValue() {
        assertDoesNotThrow(() -> Discount.of(new BigDecimal("0.01")));
    }

    @Test
    void shouldCreateDiscountWithMaximumValidValue() {
        assertDoesNotThrow(() -> Discount.of(new BigDecimal("99.99")));
    }

    @Test
    void shouldCreateDiscountWithHundredPercent() {
        Discount discount = Discount.of(new BigDecimal("100"));
        assertEquals(new BigDecimal("1.00"), discount.discount());
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsNegative() {
        BigDecimal negativeDiscount = new BigDecimal("-10");

        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> Discount.of(negativeDiscount)
        );

        assertEquals("Discount must be between 0 and 100", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsGreaterThan100() {
        BigDecimal excessiveDiscount = new BigDecimal("101");

        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> Discount.of(excessiveDiscount)
        );

        assertEquals("Discount must be between 0 and 100", exception.getMessage());
    }
}
