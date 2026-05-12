package com.demo.ecommerce.application.port.in.category.usecase;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.domain.model.product.Category;

public interface UpdateCategoryUseCase {
    Category execute(Long id, CreateCategoryCommand command);
}
