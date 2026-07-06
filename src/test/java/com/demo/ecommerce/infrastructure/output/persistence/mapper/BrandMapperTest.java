package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class BrandMapperTest {

    private final BrandMapper mapper = Mappers.getMapper(BrandMapper.class);

    @Test
    void shouldMapEntityToDomain() {
        BrandEntity entity = new BrandEntity(1L, "Nike");

        Brand brand = mapper.toDomain(entity);

        assertThat(brand.id()).isEqualTo(1L);
        assertThat(brand.name()).isEqualTo("Nike");
    }

    @Test
    void shouldMapDomainToEntity() {
        Brand brand = Brand.of(1L, "Nike");

        BrandEntity entity = mapper.toEntity(brand);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Nike");
    }
}
