package com.demo.ecommerce.infrastructure.output.persistence.adapter.category;

import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.output.persistence.entity.CategoryEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {

    private final SpringDataCategoryRepository repository;
    private final CategoryMapper categoryMapper;

    @Override
    public Category save(Category category) {
        CategoryEntity entity = repository.save(categoryMapper.toEntity(category));
        return categoryMapper.toDomain(entity);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById(id).map(categoryMapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return repository.findByName(name).map(categoryMapper::toDomain);
    }

    @Override
    public Page<Category> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(categoryMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
