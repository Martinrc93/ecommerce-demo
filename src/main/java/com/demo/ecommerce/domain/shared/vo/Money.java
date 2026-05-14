package com.demo.ecommerce.domain.shared.vo;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal money) {

    public Money{
        if (money.compareTo(BigDecimal.ZERO) < 0){
            throw new InvalidValueObjectException("Money cannot be negative");
        }
        money = money.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Money of(BigDecimal money){
        return new Money(money);
    }
    public Money sum (Money money){
        return new Money(this.money.add(money.money));
    }

    public Money applyDiscount(Discount discount){
        BigDecimal moneyWithDiscount = this.money.multiply(BigDecimal.ONE.subtract(discount.discount())).setScale(2, RoundingMode.HALF_EVEN);
        return new Money(moneyWithDiscount);
    }
}
