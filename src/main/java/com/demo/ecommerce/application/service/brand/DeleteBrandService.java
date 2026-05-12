package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.in.brand.usecase.DeleteBrandUseCase;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteBrandService implements DeleteBrandUseCase {

    private final BrandRepositoryPort brandRepositoryPort;

    @Override
    public void execute(Long id) {

        brandRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found")); //TODO
        brandRepositoryPort.deleteById(id);
    }
}
