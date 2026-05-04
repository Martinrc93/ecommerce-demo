package com.demo.ecommerce.application.service.product;

import com.demo.ecommerce.application.port.in.product.usecase.GetProductUseCase;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.exception.product.ProductIdNotFoundException;
import com.demo.ecommerce.domain.exception.product.ProductNameNotFoundException;
import com.demo.ecommerce.domain.model.product.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProductService implements GetProductUseCase {

    private final ProductRepositoryPort productRepository;

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductIdNotFoundException(id));
    }

    @Override
    public Product getByName(String name) {
        return productRepository.findByName(name)
                .orElseThrow(() -> new ProductNameNotFoundException(name));
    }

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }
}
