package com.demo.ecommerce.application.port.in.product.usecase;

import com.demo.ecommerce.application.port.in.product.command.UpdateProductDetailCommand;
import com.demo.ecommerce.domain.model.product.Product;

public interface UpdateProductUseCase {
    Product updateDetail(UpdateProductDetailCommand command);
}
