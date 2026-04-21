package com.demo.ecommerce.application.port.in.product.usecase;

import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductDetailCommand;
import com.demo.ecommerce.domain.model.product.Product;

public interface UpdateProductUseCase {
    Product updateDetail(Long id,UpdateProductDetailCommand command);
    Product update(Long id, UpdateProductCommand command);
}
