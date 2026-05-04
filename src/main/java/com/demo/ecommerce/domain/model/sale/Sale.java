package com.demo.ecommerce.domain.model.sale;


import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Discount;
import com.demo.ecommerce.domain.shared.vo.Money;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
        sale.saleDetails = new ArrayList<>();
        sale.subTotal = Money.of(BigDecimal.ZERO);
        sale.discount = Discount.of(discount);
        sale.total = Money.of(BigDecimal.ZERO);
        return sale;
    }

    public Sale addSaleDetail(SaleDetail saleDetail){
        saleDetails.add(saleDetail);

        BigDecimal detailTotal = saleDetail.getPrice().money().multiply(BigDecimal.valueOf(saleDetail.getAmount()));
        Money detailMoney = Money.of(detailTotal);

        this.subTotal = this.subTotal.sum(detailMoney);
        this.total = this.subTotal.applyDiscount(this.subTotal, this.discount);

        return this;
    }

    @Builder
    public static Sale Reconstitute(Long id,UUID userId,List<SaleDetail> saleDetails, BigDecimal subtotal, BigDecimal discount, BigDecimal total){
        Sale sale = new Sale();
        sale.id = id;
        sale.userId = userId;
        sale.saleDetails = saleDetails;
        sale.subTotal = Money.of(subtotal);
        sale.discount = Discount.of(discount);
        sale.total = Money.of(total);
        return sale;
    }
}