package com.demo.ecommerce.infrastructure.output.persistence.adapter.product;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.CategoryEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryAdapterTest {

    @Mock
    private SpringDataProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductRepositoryAdapter adapter;

    @Test
    void shouldReturnOriginalProductOnSaveEvenIfRepositoryReturnsAnotherEntity() {
        Product original = Product.reconstitute(null, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, null);
        ProductEntity toSave = new ProductEntity(null, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, null);
        ProductEntity persisted = new ProductEntity(99L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 7L);

        when(productMapper.toEntity(original)).thenReturn(toSave);
        when(productRepository.save(toSave)).thenReturn(persisted);

        Product result = adapter.save(original);

        assertThat(result).isSameAs(original);
        assertThat(result.getId()).isNull();
        assertThat(result.getVersion()).isNull();
    }

    @Test
    void shouldFindById() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        ProductEntity entity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(productMapper.toDomain(entity)).thenReturn(product);

        Optional<Product> result = adapter.findById(1L);

        assertThat(result).contains(product);
    }

    @Test
    void shouldFindByName() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        ProductEntity entity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);

        when(productRepository.findByNameContainingIgnoreCase("Running")).thenReturn(Optional.of(entity));
        when(productMapper.toDomain(entity)).thenReturn(product);

        Optional<Product> result = adapter.findByName("Running");

        assertThat(result).contains(product);
    }

    @Test
    void shouldFindAllByFilters() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        ProductEntity entity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        PageRequest pageable = PageRequest.of(0, 10);

        when(productRepository.findByFilters("Sports", "Nike", new BigDecimal("10.00"), new BigDecimal("200.00"), true, pageable))
                .thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(productMapper.toDomain(entity)).thenReturn(product);

        Page<Product> result = adapter.findAll("Sports", "Nike", new BigDecimal("10.00"), new BigDecimal("200.00"), true, pageable);

        assertThat(result.getContent()).containsExactly(product);
    }

    @Test
    void shouldFindAllLowStock() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 2, true, 2L);
        ProductEntity entity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 2, true, 2L);
        PageRequest pageable = PageRequest.of(0, 10);

        when(productRepository.findByStock(3, pageable)).thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(productMapper.toDomain(entity)).thenReturn(product);

        Page<Product> result = adapter.findAllLowStock(pageable, 3);

        assertThat(result.getContent()).containsExactly(product);
    }

    @Test
    void shouldFindAllById() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        ProductEntity entity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);

        when(productRepository.findAllById(List.of(1L))).thenReturn(List.of(entity));
        when(productMapper.toDomain(entity)).thenReturn(product);

        List<Product> result = adapter.findAllById(List.of(1L));

        assertThat(result).containsExactly(product);
    }

    @Test
    void shouldFindAllByIdsWithPessimisticLock() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        ProductEntity entity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);

        when(productRepository.findAllByIdsWithPessimisticLock(List.of(1L))).thenReturn(List.of(entity));
        when(productMapper.toDomain(entity)).thenReturn(product);

        List<Product> result = adapter.findAllByIdsWithPessimisticLock(List.of(1L));

        assertThat(result).containsExactly(product);
    }

    @Test
    void shouldSaveAll() {
        Product first = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        Product second = Product.reconstitute(2L, "Trail Shoes", "Durable trail shoes", Brand.of(2L, "Adidas"), Category.of(2L, "Outdoor"), new BigDecimal("149.99"), 5, true, 3L);
        ProductEntity firstEntity = new ProductEntity(1L, "Running Shoes", "Lightweight running shoes", new BrandEntity(1L, "Nike"), new CategoryEntity(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);
        ProductEntity secondEntity = new ProductEntity(2L, "Trail Shoes", "Durable trail shoes", new BrandEntity(2L, "Adidas"), new CategoryEntity(2L, "Outdoor"), new BigDecimal("149.99"), 5, true, 3L);

        when(productMapper.toEntity(first)).thenReturn(firstEntity);
        when(productMapper.toEntity(second)).thenReturn(secondEntity);

        adapter.saveAll(List.of(first, second));

        verify(productRepository).saveAll(List.of(firstEntity, secondEntity));
    }

    @Test
    void shouldDeleteById() {
        adapter.deleteById(1L);

        verify(productRepository).deleteById(1L);
    }
}
