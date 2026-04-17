package com.demo.ecommerce.application.port.in.sale.usecase;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.domain.model.sale.Sale;

public interface CreateSaleUseCase {
    Sale execute (CreateSaleCommand command);
}
