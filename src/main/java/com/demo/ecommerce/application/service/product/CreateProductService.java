package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.usecase.CreateProductUseCase;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.category.CategoryNotFoundException;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  CreateProductService implements CreateProductUseCase {

    private final ProductRepositoryPort productRepository;
    private final BrandRepositoryPort brandRepository;
    private final CategoryRepositoryPort categoryRepository;

    @Override
    public Product execute(CreateProductCommand command) {

        Brand brand = brandRepository.findByName(command.brand())
                .orElseThrow(() -> new NotFoundException("Brand not found: " + command.brand()));

        Category category = categoryRepository.findByName(command.category())
                .orElseThrow(() -> new CategoryNotFoundException(command.category()));

        Product product = Product.create(
                command.name(),
                command.description(),
                brand,
                category,
                command.price(),
                command.stock(),
                command.active());

        return productRepository.save(product);
    }
}
