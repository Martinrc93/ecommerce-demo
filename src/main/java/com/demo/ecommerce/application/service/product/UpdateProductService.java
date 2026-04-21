package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductDetailCommand;
import com.demo.ecommerce.application.port.in.product.usecase.UpdateProductUseCase;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.model.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public Product updateDetail(Long id,UpdateProductDetailCommand command) {

        return null;
    }
    @Override
    public Product update(Long id, UpdateProductCommand command) {

        Product productExisting = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found" + id)); //TODO

        productExisting.update(
                command.name(),
                command.description(),
                command.brand(),
                command.category(),
                command.price(),
                command.stock(),
                command.active()
        );

        return productRepository.save(productExisting);
    }
}
