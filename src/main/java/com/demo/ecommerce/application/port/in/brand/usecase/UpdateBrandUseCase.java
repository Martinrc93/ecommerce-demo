package com.demo.ecommerce.application.port.in.brand.usecase;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.domain.model.product.Brand;

public interface UpdateBrandUseCase {
    Brand execute(Long id, CreateBrandCommand command);
}
