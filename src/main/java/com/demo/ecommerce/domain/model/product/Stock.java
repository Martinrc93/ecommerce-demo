package com.demo.ecommerce.domain.model.product;

import com.demo.ecommerce.domain.exception.global.InvalidValueObjectException;

public record Stock(Integer stock) {

    public Stock {
        if (stock == null || stock < 0) {
            throw new InvalidValueObjectException("Amount must be greater than or equal to 0");
        }
    }

    public static Stock of(Integer amount) {
        return new Stock(amount);
    }

    public Stock updateStock(Integer stockToDiscount) {
        if (stockToDiscount == null || stockToDiscount <= 0) {
            throw new InvalidValueObjectException("Stock to discount must be greater than 0");
        }
        if (this.stock() < stockToDiscount) {
            throw new InvalidValueObjectException("Insufficient stock available");
        }
        return new Stock(this.stock() - stockToDiscount);
    }

}
