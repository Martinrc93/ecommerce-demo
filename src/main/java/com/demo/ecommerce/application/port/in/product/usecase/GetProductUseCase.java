package com.demo.ecommerce.application.port.in.product.usecase;

import com.demo.ecommerce.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface GetProductUseCase {
    Product getById(Long id);
    Product getByName(String name);
    Page<Product> getAll(String category, String brand, BigDecimal minPrice, BigDecimal maxPrice, Boolean active, Pageable pageable);
}
