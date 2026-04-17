package com.demo.ecommerce.application.port.in.product.usecase;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.domain.model.product.Product;

public interface CreateProductUseCase {
    Product execute (CreateProductCommand command);
}