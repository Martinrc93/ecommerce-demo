package com.demo.ecommerce.application.service.sale;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.application.port.out.SaleRepositoryPort;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.domain.model.sale.Sale;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateSaleServiceTest {

    @Test
    void shouldCreateSaleAndReduceStockUsingQuantity() {
        InMemorySaleRepository saleRepository = new InMemorySaleRepository();
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        Product product = Product.reconstitute(
                1L,
                "Laptop",
                "Portable computer",
                Brand.of(10L, "Lenovo"),
                Category.of(20L, "Tech"),
                new BigDecimal("100.00"),
                10,
                true,
                1L
        );
        productRepository.lockedProducts = List.of(product);
        CreateSaleService createSaleService = new CreateSaleService(saleRepository, productRepository);
        UUID userId = UUID.randomUUID();

        Sale sale = createSaleService.save(new CreateSaleCommand(userId, List.of(new Item(1L, 2, 9))));

        assertSame(saleRepository.savedSale, sale);
        assertEquals(userId, sale.getUserId());
        assertEquals(1, sale.getSaleDetails().size());
        assertEquals(new BigDecimal("200.00"), sale.getSubTotal().money());
        assertEquals(new BigDecimal("200.00"), sale.getTotal().money());
        assertEquals(8, product.getProductAvailability().stock().stock());
        assertEquals(1, productRepository.savedProducts.size());
        assertSame(product, productRepository.savedProducts.get(0));
    }

    @Test
    void shouldThrowWhenAnyProductIsMissing() {
        InMemorySaleRepository saleRepository = new InMemorySaleRepository();
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        CreateSaleService createSaleService = new CreateSaleService(saleRepository, productRepository);

        ProductIdNotFoundException exception = assertThrows(ProductIdNotFoundException.class,
                () -> createSaleService.save(new CreateSaleCommand(UUID.randomUUID(), List.of(new Item(99L, 2, 2)))));

        assertEquals("Product not found with id: 99", exception.getMessage());
    }

    private static class InMemorySaleRepository implements SaleRepositoryPort {
        private Sale savedSale;

        @Override
        public Sale save(Sale sale) {
            this.savedSale = sale;
            return sale;
        }

        @Override
        public Page<Sale> findAll(Pageable pageable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Sale> findById(Long id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Sale> findByDates(LocalDateTime starDate, LocalDateTime endDate, Pageable pageable) {
            throw new UnsupportedOperationException();
        }
    }

    private static class InMemoryProductRepository implements ProductRepositoryPort {
        private List<Product> lockedProducts = List.of();
        private List<Product> savedProducts = List.of();

        @Override
        public Product save(Product product) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void saveAll(List<Product> products) {
            this.savedProducts = products;
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
            return lockedProducts;
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
}
