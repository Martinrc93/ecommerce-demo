package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.command.UpdateProductDetailCommand;
import com.demo.ecommerce.application.port.in.product.usecase.UpdateProductUseCase;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.model.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateProductService implements UpdateProductUseCase {

    private final ProductRepositoryPort ProductRepository;

    @Override
    public Product updateDetail(UpdateProductDetailCommand command) {
        return null;
    }
}
