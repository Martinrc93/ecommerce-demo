package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.usecase.CreateProductUseCase;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  CreateProductService implements CreateProductUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public Product execute(CreateProductCommand command) {

        Product product = Product.create(
                command.name(),
                command.description(),
                command.brand(),
                command.category(),
                command.price(),
                command.stock(),
                command.active());

        return productRepository.save(product);
    }
}
