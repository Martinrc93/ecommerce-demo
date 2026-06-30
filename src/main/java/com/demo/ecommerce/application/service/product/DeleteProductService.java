package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.usecase.DeleteProductUseCase;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DeleteProductService implements DeleteProductUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public void execute(Long id) {

        productRepository.findById(id)
                .orElseThrow(() -> new ProductIdNotFoundException(id));

        productRepository.deleteById(id);
    }
}