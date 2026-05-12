package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.shared.vo.Discount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

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

    // Casos inválidos
    @Test
    void shouldThrowExceptionWhenDiscountIsZero() {
        assertThrows(IllegalArgumentException.class,
                () -> Discount.of(BigDecimal.ZERO));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIs100() {
        assertThrows(IllegalArgumentException.class,
                () -> Discount.of(new BigDecimal("100")));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> Discount.of(new BigDecimal("-10")));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsGreaterThan100() {
        assertThrows(IllegalArgumentException.class,
                () -> Discount.of(new BigDecimal("101")));
    }

    @Test
    void shouldThrowExceptionWithCorrectMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> Discount.of(BigDecimal.ZERO));
        assertEquals("Discount must be between 0 and 100", ex.getMessage());
    }

    @Test
    void shouldCreateDiscountViaStaticFactory() {
        assertDoesNotThrow(() -> Discount.of(new BigDecimal("25")));
    }
}
