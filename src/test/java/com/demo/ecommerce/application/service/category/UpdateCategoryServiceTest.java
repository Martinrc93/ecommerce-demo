package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.model.product.Category;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateCategoryServiceTest {

    @Test
    void shouldUpdateCategoryNamePreservingId() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        categoryRepositoryPort.storedCategory = Category.of(7L, "Tech");
        UpdateCategoryService updateCategoryService = new UpdateCategoryService(categoryRepositoryPort);

        Category updated = updateCategoryService.execute(7L, new CreateCategoryCommand("Accessories"));

        assertEquals(7L, updated.id());
        assertEquals("Accessories", updated.name());
        assertEquals(7L, categoryRepositoryPort.savedCategory.id());
        assertEquals("Accessories", categoryRepositoryPort.savedCategory.name());
    }

    @Test
    void shouldThrowWhenCategoryDoesNotExist() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        UpdateCategoryService updateCategoryService = new UpdateCategoryService(categoryRepositoryPort);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> updateCategoryService.execute(7L, new CreateCategoryCommand("Accessories")));

        assertEquals("Category not found: id : 7", exception.getMessage());
        assertNull(categoryRepositoryPort.savedCategory);
    }

    private static class InMemoryCategoryRepository implements CategoryRepositoryPort {
        private Category storedCategory;
        private Category savedCategory;

        @Override
        public Category save(Category category) {
            this.savedCategory = category;
            return category;
        }

        @Override
        public Optional<Category> findById(Long id) {
            return storedCategory != null && storedCategory.id().equals(id)
                    ? Optional.of(storedCategory)
                    : Optional.empty();
        }

        @Override
        public Optional<Category> findByName(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Category> getAll(Pageable pageable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }
}
