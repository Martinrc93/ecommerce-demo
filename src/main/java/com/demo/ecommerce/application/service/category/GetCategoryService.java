package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.usecase.GetCategoryUseCase;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.model.product.Category;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetCategoryService implements GetCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public Category getById(Long id) {
        return categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("id: " + id));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepositoryPort.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepositoryPort.getAll(pageable);
    }
}
