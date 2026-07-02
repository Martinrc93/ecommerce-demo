package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.model.product.Category;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteCategoryServiceTest {

    @Test
    void shouldDeleteCategoryWhenItExists() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        categoryRepositoryPort.storedCategory = Category.of(7L, "Tech");
        DeleteCategoryService deleteCategoryService = new DeleteCategoryService(categoryRepositoryPort);

        deleteCategoryService.execute(7L);

        assertTrue(categoryRepositoryPort.deleted);
        assertEquals(7L, categoryRepositoryPort.deletedId);
    }

    @Test
    void shouldThrowWhenCategoryDoesNotExist() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        DeleteCategoryService deleteCategoryService = new DeleteCategoryService(categoryRepositoryPort);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> deleteCategoryService.execute(7L));

        assertEquals("Category not found: id: 7", exception.getMessage());
    }

    private static class InMemoryCategoryRepository implements CategoryRepositoryPort {
        private Category storedCategory;
        private boolean deleted;
        private Long deletedId;

        @Override
        public Category save(Category category) {
            this.storedCategory = category;
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
            this.deleted = true;
            this.deletedId = id;
        }
    }
}
