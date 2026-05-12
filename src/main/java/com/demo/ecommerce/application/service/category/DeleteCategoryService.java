package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.usecase.DeleteCategoryUseCase;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public void execute(Long id) {
        categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found")); //TODO
        categoryRepositoryPort.deleteById(id);
    }
}
