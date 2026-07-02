package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.model.product.Category;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetCategoryServiceTest {

    @Test
    void shouldReturnCategoryByIdWhenItExists() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        Category stored = Category.of(7L, "Tech");
        categoryRepositoryPort.byId = Optional.of(stored);
        GetCategoryService getCategoryService = new GetCategoryService(categoryRepositoryPort);

        Category category = getCategoryService.getById(7L);

        assertSame(stored, category);
    }

    @Test
    void shouldThrowWhenCategoryByIdDoesNotExist() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        GetCategoryService getCategoryService = new GetCategoryService(categoryRepositoryPort);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> getCategoryService.getById(7L));

        assertEquals("Category not found: id: 7", exception.getMessage());
    }

    @Test
    void shouldReturnCategoryByNameWhenItExists() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        Category stored = Category.of(7L, "Tech");
        categoryRepositoryPort.byName = Optional.of(stored);
        GetCategoryService getCategoryService = new GetCategoryService(categoryRepositoryPort);

        Category category = getCategoryService.getByName("Tech");

        assertSame(stored, category);
    }

    @Test
    void shouldThrowWhenCategoryByNameDoesNotExist() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        GetCategoryService getCategoryService = new GetCategoryService(categoryRepositoryPort);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> getCategoryService.getByName("Tech"));

        assertEquals("Category not found: Tech", exception.getMessage());
    }

    @Test
    void shouldDelegatePagedListing() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Category> page = new PageImpl<>(List.of(Category.of(7L, "Tech")));
        categoryRepositoryPort.page = page;
        GetCategoryService getCategoryService = new GetCategoryService(categoryRepositoryPort);

        Page<Category> result = getCategoryService.getAll(pageable);

        assertSame(page, result);
        assertSame(pageable, categoryRepositoryPort.lastPageable);
    }

    private static class InMemoryCategoryRepository implements CategoryRepositoryPort {
        private Optional<Category> byId = Optional.empty();
        private Optional<Category> byName = Optional.empty();
        private Page<Category> page = Page.empty();
        private Pageable lastPageable;

        @Override
        public Category save(Category category) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Category> findById(Long id) {
            return byId;
        }

        @Override
        public Optional<Category> findByName(String name) {
            return byName;
        }

        @Override
        public Page<Category> getAll(Pageable pageable) {
            this.lastPageable = pageable;
            return page;
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }
}
