package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toDomain (BrandEntity entity);

    BrandEntity toEntity (Brand domain);
}
