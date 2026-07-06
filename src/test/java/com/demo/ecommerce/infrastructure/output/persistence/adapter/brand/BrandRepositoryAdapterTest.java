package com.demo.ecommerce.infrastructure.output.persistence.adapter.brand;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.BrandMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandRepositoryAdapterTest {

    @Mock
    private SpringDataBrandRepository repository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandRepositoryAdapter adapter;

    @Test
    void shouldSaveBrand() {
        Brand brand = Brand.of(1L, "Nike");
        BrandEntity entity = new BrandEntity(1L, "Nike");

        when(brandMapper.toEntity(brand)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(brandMapper.toDomain(entity)).thenReturn(brand);

        Brand result = adapter.save(brand);

        assertThat(result).isEqualTo(brand);
    }

    @Test
    void shouldFindById() {
        Brand brand = Brand.of(1L, "Nike");
        BrandEntity entity = new BrandEntity(1L, "Nike");

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(brandMapper.toDomain(entity)).thenReturn(brand);

        Optional<Brand> result = adapter.findById(1L);

        assertThat(result).contains(brand);
    }

    @Test
    void shouldFindByName() {
        Brand brand = Brand.of(1L, "Nike");
        BrandEntity entity = new BrandEntity(1L, "Nike");

        when(repository.findByName("Nike")).thenReturn(Optional.of(entity));
        when(brandMapper.toDomain(entity)).thenReturn(brand);

        Optional<Brand> result = adapter.findByName("Nike");

        assertThat(result).contains(brand);
    }

    @Test
    void shouldGetAll() {
        Brand brand = Brand.of(1L, "Nike");
        BrandEntity entity = new BrandEntity(1L, "Nike");
        PageRequest pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(brandMapper.toDomain(entity)).thenReturn(brand);

        Page<Brand> result = adapter.getAll(pageable);

        assertThat(result.getContent()).containsExactly(brand);
    }

    @Test
    void shouldDeleteById() {
        adapter.deleteById(1L);

        verify(repository).deleteById(1L);
    }
}
