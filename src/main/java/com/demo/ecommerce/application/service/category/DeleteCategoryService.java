package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.usecase.DeleteCategoryUseCase;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    @Override
    public void execute(Long id) {
        categoryRepositoryPort.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("id: " + id)); //TODO
        categoryRepositoryPort.deleteById(id);
    }
}
