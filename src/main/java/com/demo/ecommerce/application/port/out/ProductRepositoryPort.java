package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save (Product product);
    void saveAll (List<Product> products);
    Page<Product> findAll (Pageable pageable);
    Page<Product> findAllLowStock(Pageable pageable, Integer lowStock);
    Optional<Product> findById (Long id);
    List<Product> findAllById (List<Long> ids);
    Optional<Product> findByName(String name);
    Page<Product> findByCategory(String category, Pageable pageable);
    void deleteById (Long id);
}
