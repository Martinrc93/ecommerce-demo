package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateProductServiceTest {

    @Test
    void shouldUpdateProductWhenDependenciesExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        Product existing = Product.create(
                "Laptop",
                "Portable computer",
                Brand.of(1L, "Lenovo"),
                Category.of(2L, "Tech"),
                new BigDecimal("1499.99"),
                10,
                true
        );
        productRepository.byId = Optional.of(existing);
        brandRepository.brand = Optional.of(Brand.of(3L, "Logitech"));
        categoryRepository.category = Optional.of(Category.of(4L, "Accessories"));
        UpdateProductService updateProductService = new UpdateProductService(productRepository, brandRepository, categoryRepository);

        Product updated = updateProductService.update(7L, new UpdateProductCommand(
                "Mouse",
                "Wireless mouse",
                "Logitech",
                "Accessories",
                7,
                new BigDecimal("99.99"),
                false
        ));

        assertSame(existing, updated);
        assertEquals("Mouse", updated.getProductDetail().name());
        assertEquals("Wireless mouse", updated.getProductDetail().description());
        assertEquals("Logitech", updated.getProductDetail().brand().name());
        assertEquals("Accessories", updated.getProductDetail().category().name());
        assertEquals(new BigDecimal("99.99"), updated.getPrice().money());
        assertEquals(7, updated.getProductAvailability().stock().stock());
        assertEquals(false, updated.getProductAvailability().active());
        assertSame(existing, productRepository.savedProduct);
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        UpdateProductService updateProductService = new UpdateProductService(productRepository, new InMemoryBrandRepository(), new InMemoryCategoryRepository());

        ProductIdNotFoundException exception = assertThrows(ProductIdNotFoundException.class,
                () -> updateProductService.update(7L, sampleCommand()));

        assertEquals("Product not found with id: 7", exception.getMessage());
    }

    @Test
    void shouldThrowWhenBrandDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        productRepository.byId = Optional.of(Product.create(
                "Laptop",
                "Portable computer",
                Brand.of(1L, "Lenovo"),
                Category.of(2L, "Tech"),
                new BigDecimal("1499.99"),
                10,
                true
        ));
        UpdateProductService updateProductService = new UpdateProductService(productRepository, brandRepository, categoryRepository);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateProductService.update(7L, sampleCommand()));

        assertEquals("Brand not found: Logitech", exception.getMessage());
    }

    @Test
    void shouldThrowWhenCategoryDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryCategoryRepository categoryRepository = new InMemoryCategoryRepository();
        productRepository.byId = Optional.of(Product.create(
                "Laptop",
                "Portable computer",
                Brand.of(1L, "Lenovo"),
                Category.of(2L, "Tech"),
                new BigDecimal("1499.99"),
                10,
                true
        ));
        brandRepository.brand = Optional.of(Brand.of(3L, "Logitech"));
        UpdateProductService updateProductService = new UpdateProductService(productRepository, brandRepository, categoryRepository);

        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> updateProductService.update(7L, sampleCommand()));

        assertEquals("Category not found: Accessories", exception.getMessage());
    }

    private static UpdateProductCommand sampleCommand() {
        return new UpdateProductCommand(
                "Mouse",
                "Wireless mouse",
                "Logitech",
                "Accessories",
                7,
                new BigDecimal("99.99"),
                false
        );
    }

    private static class InMemoryProductRepository implements ProductRepositoryPort {
        private Optional<Product> byId = Optional.empty();
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
            return byId;
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
