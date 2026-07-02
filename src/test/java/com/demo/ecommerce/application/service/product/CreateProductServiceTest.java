package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateProductServiceTest {

    @Test
    void shouldCreateAndSaveProductWhenBrandAndCategoryExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        brandRepository.brand = Optional.of(Brand.of(1L, "Lenovo"));
        categoryRepository.category = Optional.of(Category.of(2L, "Tech"));
        CreateProductService createProductService = new CreateProductService(productRepository, brandRepository, categoryRepository);

        Product product = createProductService.execute(new CreateProductCommand(
                "Laptop",
                "Portable computer",
                "Lenovo",
                "Tech",
                new BigDecimal("1499.99"),
                10,
                true
        ));

        assertNotNull(productRepository.savedProduct);
        assertEquals("Laptop", product.getProductDetail().name());
        assertEquals("Lenovo", product.getProductDetail().brand().name());
        assertEquals("Tech", product.getProductDetail().category().name());
        assertEquals(new BigDecimal("1499.99"), product.getPrice().money());
        assertEquals(10, product.getProductAvailability().stock().stock());
        assertTrue(product.getProductAvailability().active());
    }

    @Test
    void shouldThrowWhenBrandDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        CreateProductService createProductService = new CreateProductService(productRepository, brandRepository, categoryRepository);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> createProductService.execute(new CreateProductCommand(
                "Laptop",
                "Portable computer",
                "Lenovo",
                "Tech",
                new BigDecimal("1499.99"),
                10,
                true
        )));

        assertEquals("Brand not found: Lenovo", exception.getMessage());
    }

    @Test
    void shouldThrowWhenCategoryDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        brandRepository.brand = Optional.of(Brand.of(1L, "Lenovo"));
        CreateProductService createProductService = new CreateProductService(productRepository, brandRepository, categoryRepository);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> createProductService.execute(new CreateProductCommand(
                "Laptop",
                "Portable computer",
                "Lenovo",
                "Tech",
                new BigDecimal("1499.99"),
                10,
                true
        )));

        assertEquals("Category not found: Tech", exception.getMessage());
    }

    private static class InMemoryProductRepository implements ProductRepositoryPort {
        private Product savedProduct;

        @Override
        public Product save(Product product) {
            this.savedProduct = product;
            return product;
        }

        @Override
        public void saveAll(List<Product> products) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Product> findAll(String category, String brand, BigDecimal minPrice, BigDecimal maxPrice, Boolean active, Pageable pageable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Product> findAllLowStock(Pageable pageable, Integer lowStock) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Product> findById(Long id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Product> findAllById(List<Long> ids) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Product> findAllByIdsWithPessimisticLock(List<Long> ids) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Product> findByName(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class InMemoryBrandRepository implements BrandRepositoryPort {
        private Optional<Brand> brand = Optional.empty();

        @Override
        public Brand save(Brand brand) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Brand> findById(Long id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Brand> findByName(String name) {
            return brand;
        }

        @Override
        public Page<Brand> getAll(Pageable pageable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }

    private static class InMemoryCategoryRepository implements CategoryRepositoryPort {
        private Optional<Category> category = Optional.empty();

        @Override
        public Category save(Category category) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Category> findById(Long id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Category> findByName(String name) {
            return category;
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
