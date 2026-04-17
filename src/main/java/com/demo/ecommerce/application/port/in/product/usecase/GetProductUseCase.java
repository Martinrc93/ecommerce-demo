package com.demo.ecommerce.application.port.in.product.usecase;

import com.demo.ecommerce.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetProductUseCase {
    Product getById(Long id);
    Product getByName(String name);
    Page<Product> getAll(Pageable pageable);
    Page<Product> getByCategory(String category, Pageable pageable);
}
