package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.application.port.in.category.usecase.UpdateCategoryUseCase;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.model.product.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Category execute(Long id, CreateCategoryCommand command) {

        Category category = categoryRepositoryPort.findById(id).
                orElseThrow(() -> new CategoryNotFoundException("id : " + id.toString()));

        return categoryRepositoryPort.save(Category.of(category.id(), command.name()));
    }
}
