package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.request.BrandDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

class BrandDtoMapperTest {

    private final BrandDtoMapper mapper = Mappers.getMapper(BrandDtoMapper.class);

    @Test
    void shouldMapBrandToResponse() {
        Brand brand = Brand.of(1L, "Nike");

        BrandDtoResponse response = mapper.toResponse(brand);

        assertThat(response.name()).isEqualTo("Nike");
    }

    @Test
    void shouldMapRequestToCommand() {
        BrandDtoRequest request = new BrandDtoRequest("Nike");

        CreateBrandCommand command = mapper.toCommand(request);

        assertThat(command.name()).isEqualTo("Nike");
    }
}
