package com.demo.ecommerce.domain.model.product;

import com.demo.ecommerce.domain.shared.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    private Long id;
    private ProductDetail productDetail;
    private Money price;
    private ProductAvailability productAvailability;

    private Product(ProductDetail productDetail, Money price, ProductAvailability productAvailability){
        this.productDetail = productDetail;
        this.price = price;
        this.productAvailability = productAvailability;
    }
    private Product(Long id,ProductDetail productDetail, Money price, ProductAvailability productAvailability){
        this.id = id;
        this.productDetail = productDetail;
        this.price = price;
        this.productAvailability = productAvailability;
    }

    public static Product create(String name,String description, String brand, String category,BigDecimal price, Integer stock, boolean active){

        ProductDetail productDetail = ProductDetail.of(name,description,brand,category);
        Money money = com.demo.ecommerce.domain.shared.vo.Money.of(price);
        ProductAvailability productAvailability= ProductAvailability.of(stock,active);

        return new Product(productDetail,money,productAvailability);
    }

    @Builder
    public static Product reconstitute(Long id,String name,String description, String brand, String category,BigDecimal price, Integer stock, boolean active){

        ProductDetail productDetail = ProductDetail.of(name,description,brand,category);
        Money money = com.demo.ecommerce.domain.shared.vo.Money.of(price);
        ProductAvailability productAvailability= ProductAvailability.of(stock,active);

        return new Product(id,productDetail,money,productAvailability);
    }

    public Product update(String name,String description, String brand, String category,BigDecimal price, Integer stock, boolean active){

        this.productDetail = ProductDetail.of(name,description,brand,category);
        this.price = Money.of(price);
        this.productAvailability = ProductAvailability.of(stock,active);
        return this;
    }

    public Product updateStock(Integer stockToDiscount){
        this.productAvailability.updateStock(productAvailability.stock(),stockToDiscount);
        return this;
    }

} 
