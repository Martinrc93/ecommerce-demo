package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void shouldCreateProductWithCurrentDomainObjects() {
        Product product = Product.create(
                "Laptop",
                "Portable computer",
                Brand.of(1L, "Lenovo"),
                Category.of(2L, "Tech"),
                new BigDecimal("1499.995"),
                10,
                true
        );

        assertNull(product.getId());
        assertEquals("Laptop", product.getProductDetail().name());
        assertEquals("Portable computer", product.getProductDetail().description());
        assertEquals("Lenovo", product.getProductDetail().brand().name());
        assertEquals("Tech", product.getProductDetail().category().name());
        assertEquals(new BigDecimal("1500.00"), product.getPrice().money());
        assertEquals(10, product.getProductAvailability().stock().stock());
        assertTrue(product.getProductAvailability().active());
    }

    @Test
    void shouldUpdateProductFieldsInPlace() {
        Product product = Product.reconstitute(
                10L,
                "Laptop",
                "Portable computer",
                Brand.of(1L, "Lenovo"),
                Category.of(2L, "Tech"),
                new BigDecimal("1499.99"),
                10,
                true,
                3L
        );

        Product updated = product.update(
                "Mouse",
                "Wireless mouse",
                Brand.of(3L, "Logitech"),
                Category.of(4L, "Accessories"),
                new BigDecimal("99.995"),
                7,
                false
        );

        assertSame(product, updated);
        assertEquals(10L, updated.getId());
        assertEquals("Mouse", updated.getProductDetail().name());
        assertEquals("Wireless mouse", updated.getProductDetail().description());
        assertEquals("Logitech", updated.getProductDetail().brand().name());
        assertEquals("Accessories", updated.getProductDetail().category().name());
        assertEquals(new BigDecimal("100.00"), updated.getPrice().money());
        assertEquals(7, updated.getProductAvailability().stock().stock());
        assertFalse(updated.getProductAvailability().active());
    }

    @Test
    void shouldDecreaseStockThroughProductAvailability() {
        Product product = Product.create(
                "Laptop",
                "Portable computer",
                Brand.of("Lenovo"),
                Category.of("Tech"),
                new BigDecimal("1499.99"),
                10,
                true
        );

        Product updated = product.updateStock(4);

        assertSame(product, updated);
        assertEquals(6, updated.getProductAvailability().stock().stock());
        assertTrue(updated.getProductAvailability().active());
    }
}
