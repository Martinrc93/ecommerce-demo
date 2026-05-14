package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save (Product product);
    void saveAll (List<Product> products);
    Page<Product> findAll (String category, String brand, BigDecimal minPrice, BigDecimal maxPrice, Boolean active, Pageable pageable);
    Page<Product> findAllLowStock(Pageable pageable, Integer lowStock);
    Optional<Product> findById (Long id);
    List<Product> findAllById (List<Long> ids);
    List<Product> findAllByIdsWithPessimisticLock(List<Long> ids);
    Optional<Product> findByName(String name);
    void deleteById (Long id);
}
