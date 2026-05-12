package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.in.brand.usecase.GetBrandUseCase;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.model.product.Brand;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetBrandService implements GetBrandUseCase {

    private final BrandRepositoryPort brandRepositoryPort;

    @Override
    public Brand getById(Long id) {
        return brandRepositoryPort.findById(id).
                orElseThrow(() -> new RuntimeException("Brand not found")); //TODO
    }

    @Override
    public Brand getByName(String name) {
        return brandRepositoryPort.findByName(name).
                orElseThrow(() -> new RuntimeException("Brand not found")); //TODO
    }

    @Override
    public Page<Brand> getAll(Pageable pageable) {
        return brandRepositoryPort.getAll(pageable);
    }
}
