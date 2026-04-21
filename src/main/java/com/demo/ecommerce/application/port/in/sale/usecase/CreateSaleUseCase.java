package com.demo.ecommerce.application.port.in.sale.usecase;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;

public interface CreateSaleUseCase {
    Sale save (CreateSaleCommand command);
    SaleDetail save(Item command);
}
