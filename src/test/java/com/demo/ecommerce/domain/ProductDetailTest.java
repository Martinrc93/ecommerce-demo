package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.product.ProductDetail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductDetailTest {

    @Test
    void shouldCreateProductDetailUsingBrandAndCategoryNames() {
        ProductDetail detail = ProductDetail.of(
                "Laptop",
                "Portable computer",
                "Lenovo",
                "Tech"
        );

        assertEquals("Laptop", detail.name());
        assertEquals("Portable computer", detail.description());
        assertEquals("Lenovo", detail.brand().name());
        assertEquals("Tech", detail.category().name());
    }
}
