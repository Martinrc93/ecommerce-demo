package com.demo.ecommerce.domain.model.product;

public record Stock(Integer stock) {

    public Stock {
        if (stock == null || stock <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    public static Stock of(Integer amount) {
        return new Stock(amount);
    }

    public Stock updateStock(Integer stockToDiscount) {
        if (stockToDiscount == null || stockToDiscount <= 0) {
            throw new IllegalArgumentException("Stock to discount must be greater than 0");
        }
        return new Stock(this.stock() - stockToDiscount);
    }

}