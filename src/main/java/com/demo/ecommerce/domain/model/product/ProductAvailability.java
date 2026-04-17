package com.demo.ecommerce.domain.model.product;

public record ProductAvailability(Stock stock,boolean active) {

    public static ProductAvailability of(Integer stock, boolean active) {
        return new ProductAvailability(Stock.of(stock), active);
    }

}
