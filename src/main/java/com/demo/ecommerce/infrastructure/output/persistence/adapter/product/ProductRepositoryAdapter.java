package com.demo.ecommerce.infrastructure.output.persistence.adapter.product;

import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        productRepository.save(productEntity);
        return product;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDomain);
    }

    @Override
    public Page<Product> findAllLowStock(Pageable pageable, Integer lowStock) {
        return productRepository.findByStock(lowStock, pageable)
                .map(productMapper::toDomain);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id).map(productMapper::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .map(productMapper::toDomain);
    }

    @Override
    public Page<Product> findByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable)
                .map(productMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllById(List<Long> ids) {

        List<ProductEntity> listEntity = productRepository.findAllById(ids);
        return listEntity.stream().map(productMapper::toDomain).toList();
    }

    @Override
    public void saveAll(List<Product> products) {

        List<ProductEntity> productEntities = products.stream().map(productMapper::toEntity).toList();
        productRepository.saveAll(productEntities);
    }
}
