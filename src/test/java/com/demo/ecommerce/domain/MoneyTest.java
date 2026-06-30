package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;
import com.demo.ecommerce.domain.shared.vo.Discount;
import com.demo.ecommerce.domain.shared.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoneySuccessfully() {
        Money money = Money.of(new BigDecimal("100"));
        assertEquals(new BigDecimal("100.00"), money.money());
    }

    @Test
    void shouldCreateMoneyWithZero() {
        Money money = Money.of(BigDecimal.ZERO);
        assertEquals(new BigDecimal("0.00"), money.money());
    }

    @Test
    void shouldRoundToTwoDecimalPlaces() {
        Money money = Money.of(new BigDecimal("10.555"));
        assertEquals(new BigDecimal("10.56"), money.money());
    }

    @Test
    void shouldThrowExceptionWhenMoneyIsNegative() {
        BigDecimal negativeAmount = new BigDecimal("-1");

        InvalidValueObjectException exception = assertThrows(
                InvalidValueObjectException.class,
                () -> Money.of(negativeAmount)
        );

        assertEquals("Money cannot be negative", exception.getMessage());
    }

    @Test
    void shouldSumTwoMoneyValues() {
        Money first = Money.of(new BigDecimal("100"));
        Money second = Money.of(new BigDecimal("50"));

        Money result = first.sum(second);

        assertEquals(new BigDecimal("150.00"), result.money());
    }

    @Test
    void shouldSumWithZero() {
        Money first = Money.of(new BigDecimal("100"));
        Money second = Money.of(BigDecimal.ZERO);

        assertEquals(new BigDecimal("100.00"), first.sum(second).money());
    }

    @Test
    void shouldApplyDiscountCorrectly() {
        Money money = Money.of(new BigDecimal("100"));
        Discount discount = Discount.of(new BigDecimal("10"));

        Money result = money.applyDiscount(discount);

        assertEquals(new BigDecimal("90.00"), result.money());
    }

    @Test
    void shouldApplyDiscountWithRounding() {
        Money money = Money.of(new BigDecimal("99.99"));
        Discount discount = Discount.of(new BigDecimal("50"));

        Money result = money.applyDiscount(discount);

        assertEquals(new BigDecimal("50.00"), result.money());
    }

    @Test
    void shouldApplyZeroDiscountWithoutChangingMoney() {
        Money money = Money.of(new BigDecimal("42.42"));
        Discount discount = Discount.of(BigDecimal.ZERO);

        Money result = money.applyDiscount(discount);

        assertEquals(new BigDecimal("42.42"), result.money());
    }
}
