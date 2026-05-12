package com.demo.ecommerce.application.port.in.brand.usecase;

import com.demo.ecommerce.domain.model.product.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetBrandUseCase {
    Brand getById(Long id);
    Brand getByName(String name);
    Page<Brand> getAll(Pageable pageable);
}
