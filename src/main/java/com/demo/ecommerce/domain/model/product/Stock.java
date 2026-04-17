package com.demo.ecommerce.domain.model.product;

public record Stock(Integer stock) {

    public Stock {
        if (stock <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    public static Stock of(Integer amount){
        return new Stock(amount);
    }

    public static Stock updateStock(Stock stock, Integer stockToDiscount){
        return new Stock(stock.stock - stockToDiscount);
    }

}
