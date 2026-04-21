package com.demo.ecommerce.domain.model.saledetail;

import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.domain.shared.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SaleDetail {

    private Long id;
    private Long saleId;
    private Long productId;
    private Integer amount;
    private Money price;
    private BigDecimal discount;

    public static SaleDetail create(Long productId, Integer amount, Money price, BigDecimal discount){
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.productId = productId;
        saleDetail.amount = amount;
        saleDetail.price = price;
        saleDetail.discount = discount;
        return saleDetail;
    }

    @Builder
    public SaleDetail reconstitute(Long id, Long saleId, Long productId, Integer amount, Money price, BigDecimal discount)
    {
        SaleDetail saleDetail = new SaleDetail();
        saleDetail.id = id;
        saleDetail.saleId = saleId;
        saleDetail.productId = productId;
        saleDetail.amount = amount;
        saleDetail.price = price;
        saleDetail.discount = discount;
        return saleDetail;
    }

}
