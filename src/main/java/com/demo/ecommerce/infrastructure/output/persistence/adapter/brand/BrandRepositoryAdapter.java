package com.demo.ecommerce.infrastructure.output.persistence.adapter.brand;

import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.BrandMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class BrandRepositoryAdapter implements BrandRepositoryPort {

    private final SpringDataBrandRepository repository;
    private final BrandMapper brandMapper;

    @Override
    public Brand save(Brand brand) {
        BrandEntity entity =repository.save(brandMapper.toEntity(brand));
        return brandMapper.toDomain(entity);
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return repository.findById(id).map(brandMapper::toDomain);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return repository.findByName(name).map(brandMapper::toDomain);
    }

    @Override
    public Page<Brand> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(brandMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
