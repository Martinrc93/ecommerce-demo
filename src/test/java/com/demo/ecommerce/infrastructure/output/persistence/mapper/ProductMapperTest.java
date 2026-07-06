package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.CategoryEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void shouldMapDomainToEntity() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight running shoes", Brand.of(1L, "Nike"), Category.of(1L, "Sports"), new BigDecimal("129.99"), 10, true, 2L);

        ProductEntity entity = mapper.toEntity(product);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getName()).isEqualTo("Running Shoes");
        assertThat(entity.getDescription()).isEqualTo("Lightweight running shoes");
        assertThat(entity.getBrand().getId()).isEqualTo(1L);
        assertThat(entity.getBrand().getName()).isEqualTo("Nike");
        assertThat(entity.getCategory().getId()).isEqualTo(1L);
        assertThat(entity.getCategory().getName()).isEqualTo("Sports");
        assertThat(entity.getPrice()).isEqualByComparingTo("129.99");
        assertThat(entity.getStock()).isEqualTo(10);
        assertThat(entity.isActive()).isTrue();
        assertThat(entity.getVersion()).isEqualTo(2L);
    }

    @Test
    void shouldMapEntityToDomain() {
        ProductEntity entity = new ProductEntity(
                1L,
                "Running Shoes",
                "Lightweight running shoes",
                new BrandEntity(1L, "Nike"),
                new CategoryEntity(1L, "Sports"),
                new BigDecimal("129.99"),
                10,
                true,
                2L
        );

        Product product = mapper.toDomain(entity);

        assertThat(product.getId()).isEqualTo(1L);
        assertThat(product.getProductDetail().name()).isEqualTo("Running Shoes");
        assertThat(product.getProductDetail().description()).isEqualTo("Lightweight running shoes");
        assertThat(product.getProductDetail().brand().id()).isEqualTo(1L);
        assertThat(product.getProductDetail().brand().name()).isEqualTo("Nike");
        assertThat(product.getProductDetail().category().id()).isEqualTo(1L);
        assertThat(product.getProductDetail().category().name()).isEqualTo("Sports");
        assertThat(product.getPrice().money()).isEqualByComparingTo("129.99");
        assertThat(product.getProductAvailability().stock().stock()).isEqualTo(10);
        assertThat(product.getProductAvailability().active()).isTrue();
        assertThat(product.getVersion()).isEqualTo(2L);
    }
}
