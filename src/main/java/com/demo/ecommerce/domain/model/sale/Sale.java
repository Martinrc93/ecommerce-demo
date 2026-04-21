package com.demo.ecommerce.domain.model.sale;


import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Discount;
import com.demo.ecommerce.domain.shared.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale {

    private Long id;
    private UUID userId;
    private List<SaleDetail> saleDetails;
    private Money subTotal;
    private Discount discount;
    private Money total;

    public static Sale create(UUID userId, BigDecimal discount){
        Sale sale = new Sale();
        sale.userId = userId;
        sale.subTotal = Money.of(BigDecimal.ZERO);
        sale.discount = Discount.of(discount);
        return sale;
    }

    public Sale addSaleDetail(SaleDetail saleDetail){
        saleDetails.add(saleDetail);
        subTotal.sum(saleDetail.getPrice());
        total = subTotal.applyDiscount(subTotal,discount);
        return this;
    }

    @Builder
    public Sale Reconstitute(Long id,UUID userId,List<SaleDetail> saleDetails, BigDecimal subtotal, BigDecimal discount, BigDecimal total){
        this.id = id;
        this.userId = userId;
        this.saleDetails = saleDetails;
        this.subTotal = Money.of(subtotal);
        this.discount = Discount.of(discount);
        this.total = Money.of(total);
        return this;
    }
}