package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.product.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BrandRepositoryPort {
    Brand save(Brand brand);
    Optional<Brand> findById(Long id);
    Optional<Brand> findByName(String name);
    Page<Brand> getAll(Pageable pageable);
    void deleteById(Long id);
}
