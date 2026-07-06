package com.demo.ecommerce.infrastructure.output.persistence.adapter.category;

import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.output.persistence.entity.CategoryEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryRepositoryAdapterTest {

    @Mock
    private SpringDataCategoryRepository repository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryRepositoryAdapter adapter;

    @Test
    void shouldSaveCategory() {
        Category category = Category.of(1L, "Shoes");
        CategoryEntity entity = new CategoryEntity(1L, "Shoes");

        when(categoryMapper.toEntity(category)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(categoryMapper.toDomain(entity)).thenReturn(category);

        Category result = adapter.save(category);

        assertThat(result).isEqualTo(category);
    }

    @Test
    void shouldFindById() {
        Category category = Category.of(1L, "Shoes");
        CategoryEntity entity = new CategoryEntity(1L, "Shoes");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(categoryMapper.toDomain(entity)).thenReturn(category);

        Optional<Category> result = adapter.findById(1L);

        assertThat(result).contains(category);
    }

    @Test
    void shouldFindByName() {
        Category category = Category.of(1L, "Shoes");
        CategoryEntity entity = new CategoryEntity(1L, "Shoes");

        when(repository.findByName("Shoes")).thenReturn(Optional.of(entity));
        when(categoryMapper.toDomain(entity)).thenReturn(category);

        Optional<Category> result = adapter.findByName("Shoes");

        assertThat(result).contains(category);
    }

    @Test
    void shouldGetAll() {
        Category category = Category.of(1L, "Shoes");
        CategoryEntity entity = new CategoryEntity(1L, "Shoes");
        PageRequest pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(categoryMapper.toDomain(entity)).thenReturn(category);

        Page<Category> result = adapter.getAll(pageable);

        assertThat(result.getContent()).containsExactly(category);
    }

    @Test
    void shouldDeleteById() {
        adapter.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
