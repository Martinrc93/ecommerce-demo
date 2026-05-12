package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandDtoMapper {

    BrandDtoResponse toResponse(Brand brand);
}
