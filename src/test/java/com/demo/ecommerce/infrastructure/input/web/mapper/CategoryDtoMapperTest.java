package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.input.web.dto.category.request.CategoryDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryDtoMapperTest {

    private final CategoryDtoMapper mapper = Mappers.getMapper(CategoryDtoMapper.class);

    @Test
    void shouldMapCategoryToResponse() {
        Category category = Category.of(1L, "Shoes");

        CategoryDtoResponse response = mapper.toResponse(category);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Shoes");
    }

    @Test
    void shouldMapRequestToCommand() {
        CategoryDtoRequest request = new CategoryDtoRequest("Shoes");

        CreateCategoryCommand command = mapper.toCommand(request);

        assertThat(command.name()).isEqualTo("Shoes");
    }
}
