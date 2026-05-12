package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.in.brand.usecase.CreateBrandUseCase;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.model.product.Brand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateBrandService implements CreateBrandUseCase {

    private final BrandRepositoryPort brandRepositoryPort;

    @Override
    public Brand execute(CreateBrandCommand command) {
        return brandRepositoryPort.save(Brand.of(command.name()));
    }
}
