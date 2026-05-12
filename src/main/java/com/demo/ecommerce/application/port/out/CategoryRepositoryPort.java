package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Long id);
    Optional<Category> findByName(String name);
    Page<Category> getAll(Pageable pageable);
    void deleteById(Long id);
}
