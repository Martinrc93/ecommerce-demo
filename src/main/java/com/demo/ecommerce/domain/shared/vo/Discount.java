package com.demo.ecommerce.domain.shared.vo;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Discount(BigDecimal discount) {

    public Discount{
        if (discount.compareTo(BigDecimal.ZERO) < 0 || discount.compareTo(BigDecimal.valueOf(100)) > 0){
            throw new InvalidValueObjectException("Discount must be between 0 and 100");
        }

        discount = discount.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    public static Discount of(BigDecimal discount){
        return new Discount(discount);
    }
}
