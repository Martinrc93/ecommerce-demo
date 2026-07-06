package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.output.persistence.entity.CategoryEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryMapperTest {

    private final CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);

    @Test
    void shouldMapEntityToDomain() {
        CategoryEntity entity = new CategoryEntity(1L, "Shoes");

        Category category = mapper.toDomain(entity);

        assertThat(category.id()).isEqualTo(1L);
        assertThat(category.name()).isEqualTo("Shoes");
    }

    @Test
    void shouldMapDomainToEntity() {
        Category category = Category.of(1L, "Shoes");

        CategoryEntity entity = mapper.toEntity(category);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Shoes");
    }
}
