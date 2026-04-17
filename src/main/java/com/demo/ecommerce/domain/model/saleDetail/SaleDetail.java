package com.demo.ecommerce.domain.model.saleDetail;

import com.demo.ecommerce.domain.model.product.Product;

import java.math.BigDecimal;

public class SaleDetail {

    private Long id;
    private Long saleId;
    private Product product;
    private Integer amount;
    private BigDecimal price;
    private BigDecimal discount;


}
