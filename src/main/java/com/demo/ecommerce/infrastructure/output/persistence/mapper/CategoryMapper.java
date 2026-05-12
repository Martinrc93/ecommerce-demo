package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.output.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toDomain (CategoryEntity entity);

    CategoryEntity toEntity (Category domain);

}
