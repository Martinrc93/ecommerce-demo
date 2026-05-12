package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.application.port.in.category.usecase.CreateCategoryUseCase;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.model.product.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCategoryService implements CreateCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Category execute(CreateCategoryCommand command) {
        return categoryRepositoryPort.save(Category.of(command.name()));
    }
}
