package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.input.web.dto.category.request.CategoryDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    CategoryDtoResponse toResponse(Category category);

    CreateCategoryCommand toCommand(CategoryDtoRequest request);

}
