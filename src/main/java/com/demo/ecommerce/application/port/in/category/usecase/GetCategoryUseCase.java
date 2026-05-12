package com.demo.ecommerce.application.port.in.category.usecase;

import com.demo.ecommerce.domain.model.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetCategoryUseCase {
    Category getById(Long id);
    Category getByName(String name);
    Page<Category> getAll(Pageable pageable);
}
