package com.demo.ecommerce.domain.model.sale;


import com.demo.ecommerce.domain.model.saleDetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Discount;
import com.demo.ecommerce.domain.shared.vo.Money;

import java.util.List;
import java.util.UUID;

public class Sale {

    private Long id;
    private UUID userId;
    private List<SaleDetail> saleDetails;
    private Money subTotal;
    private Discount discount;
    private Money total;


}
