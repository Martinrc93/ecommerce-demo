package com.demo.ecommerce.domain.model.product;

public record ProductAvailability(Stock stock,boolean active) {

    public static ProductAvailability of(Integer stock, boolean active) {
        return new ProductAvailability(Stock.of(stock), active);
    }

    public ProductAvailability updateStock(Stock stock, Integer stockToDiscount){
        return new ProductAvailability(stock.updateStock(stock,stockToDiscount),active);
    }
}
