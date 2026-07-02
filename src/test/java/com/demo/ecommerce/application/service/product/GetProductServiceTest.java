package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import com.demo.ecommerce.domain.exception.product.ProductNameNotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetProductServiceTest {

    @Test
    void shouldReturnProductByIdWhenItExists() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        Product stored = sampleProduct();
        productRepository.byId = Optional.of(stored);
        GetProductService getProductService = new GetProductService(productRepository);

        Product product = getProductService.getById(7L);

        assertSame(stored, product);
    }

    @Test
    void shouldThrowWhenProductByIdDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        GetProductService getProductService = new GetProductService(productRepository);

        ProductIdNotFoundException exception = assertThrows(ProductIdNotFoundException.class,
                () -> getProductService.getById(7L));

        assertEquals("Product not found with id: 7", exception.getMessage());
    }

    @Test
    void shouldReturnProductByNameWhenItExists() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        Product stored = sampleProduct();
        productRepository.byName = Optional.of(stored);
        GetProductService getProductService = new GetProductService(productRepository);

        Product product = getProductService.getByName("Laptop");

        assertSame(stored, product);
    }

    @Test
    void shouldThrowWhenProductByNameDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        GetProductService getProductService = new GetProductService(productRepository);

        ProductNameNotFoundException exception = assertThrows(ProductNameNotFoundException.class,
                () -> getProductService.getByName("Laptop"));

        assertEquals("Laptop", exception.getMessage());
    }

    @Test
    void shouldDelegateFilteredListing() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(List.of(sampleProduct()));
        productRepository.page = page;
        GetProductService getProductService = new GetProductService(productRepository);

        Page<Product> result = getProductService.getAll("Tech", "Lenovo", new BigDecimal("1000"), new BigDecimal("2000"), true, pageable);

        assertSame(page, result);
        assertEquals("Tech", productRepository.lastCategory);
        assertEquals("Lenovo", productRepository.lastBrand);
        assertEquals(new BigDecimal("1000"), productRepository.lastMinPrice);
        assertEquals(new BigDecimal("2000"), productRepository.lastMaxPrice);
        assertEquals(true, productRepository.lastActive);
        assertSame(pageable, productRepository.lastPageable);
    }

    private static Product sampleProduct() {
        return Product.create(
                "Laptop",
                "Portable computer",
                Brand.of(1L, "Lenovo"),
                Category.of(2L, "Tech"),
                new BigDecimal("1499.99"),
                10,
                true
        );
    }

    private static class InMemoryProductRepository implements ProductRepositoryPort {
        private Optional<Product> byId = Optional.empty();
        private Optional<Product> byName = Optional.empty();
        private Page<Product> page = Page.empty();
        private String lastCategory;
        private String lastBrand;
        private BigDecimal lastMinPrice;
        private BigDecimal lastMaxPrice;
        private Boolean lastActive;
        private Pageable lastPageable;

        @Override
        public Product save(Product product) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void saveAll(List<Product> products) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Product> findAll(String category, String brand, BigDecimal minPrice, BigDecimal maxPrice, Boolean active, Pageable pageable) {
            this.lastCategory = category;
            this.lastBrand = brand;
            this.lastMinPrice = minPrice;
            this.lastMaxPrice = maxPrice;
            this.lastActive = active;
            this.lastPageable = pageable;
            return page;
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
            return byName;
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }
}
