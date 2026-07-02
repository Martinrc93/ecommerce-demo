package com.demo.ecommerce.application.service.category;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.domain.model.product.Category;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CreateCategoryServiceTest {

    @Test
    void shouldSaveCategoryCreatedFromCommandName() {
        InMemoryCategoryRepository categoryRepositoryPort = new InMemoryCategoryRepository();
        CreateCategoryService createCategoryService = new CreateCategoryService(categoryRepositoryPort);

        Category category = createCategoryService.execute(new CreateCategoryCommand("Tech"));

        assertNull(category.id());
        assertEquals("Tech", category.name());
        assertNotNull(categoryRepositoryPort.savedCategory);
        assertEquals("Tech", categoryRepositoryPort.savedCategory.name());
    }

    private static class InMemoryCategoryRepository implements CategoryRepositoryPort {
        private Category savedCategory;

        @Override
        public Category save(Category category) {
            this.savedCategory = category;
            return category;
        }

        @Override
        public Optional<Category> findById(Long id) {
            throw new UnsupportedOperationException();
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
