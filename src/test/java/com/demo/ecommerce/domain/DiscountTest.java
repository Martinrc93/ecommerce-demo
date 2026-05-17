package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
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
    void shouldThrowExceptionWhenDiscountIsNegative() {
        BigDecimal negativeDiscount = new BigDecimal("-10");

        assertThrows(InvalidValueObjectException.class,
                () -> Discount.of(negativeDiscount));
    }

    @Test
    void shouldThrowExceptionWhenDiscountIsGreaterThan100() {
        BigDecimal excessiveDiscount = new BigDecimal("101");

        assertThrows(InvalidValueObjectException.class,
                () -> Discount.of(excessiveDiscount));
    }

    @Test
    void shouldCreateDiscountViaStaticFactory() {
        assertDoesNotThrow(() -> Discount.of(new BigDecimal("25")));
    }
}
