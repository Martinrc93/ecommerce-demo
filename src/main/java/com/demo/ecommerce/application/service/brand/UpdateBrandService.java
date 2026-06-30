package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.in.brand.usecase.UpdateBrandUseCase;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateBrandService implements UpdateBrandUseCase {

    private final BrandRepositoryPort brandRepositoryPort;

    @Override
    public Brand execute(Long id, CreateBrandCommand command) {

        Brand brand = brandRepositoryPort.findById(id).
                orElseThrow(() -> new NotFoundException("Brand not found with id: " + id));

        return brandRepositoryPort.save(Brand.of(brand.id(), command.name()));
    }
}
