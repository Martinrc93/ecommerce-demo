package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import com.demo.ecommerce.domain.model.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteProductServiceTest {

    @Test
    void shouldDeleteProductWhenItExists() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productRepository.product = Optional.of(Product.create(
                "Laptop",
                "Portable computer",
                com.demo.ecommerce.domain.model.product.Brand.of(1L, "Lenovo"),
                com.demo.ecommerce.domain.model.product.Category.of(2L, "Tech"),
                new BigDecimal("1499.99"),
                10,
                true
        ));
        DeleteProductService deleteProductService = new DeleteProductService(productRepository);

        deleteProductService.execute(7L);

        assertTrue(productRepository.deleted);
        assertEquals(7L, productRepository.deletedId);
    }

    @Test
    void shouldThrowWhenProductDoesNotExist() {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        DeleteProductService deleteProductService = new DeleteProductService(productRepository);

        ProductIdNotFoundException exception = assertThrows(ProductIdNotFoundException.class,
                () -> deleteProductService.execute(7L));

        assertEquals("Product not found with id: 7", exception.getMessage());
    }

    private static class InMemoryProductRepository implements ProductRepositoryPort {
        private Optional<Product> product = Optional.empty();
        private boolean deleted;
        private Long deletedId;

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
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Product> findAllLowStock(Pageable pageable, Integer lowStock) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Product> findById(Long id) {
            return product;
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
            this.deleted = true;
            this.deletedId = id;
        }
    }
}
