package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.CreateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.UpdateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.response.GeneralProductResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductDtoMapperTest {

    private final ProductDtoMapper mapper = Mappers.getMapper(ProductDtoMapper.class);

    @Test
    void shouldMapCreateRequestToCommand() {
        CreateProductRequest request = new CreateProductRequest("Running Shoes", "Lightweight shoes", "Nike", "Sports", new BigDecimal("129.99"), 10, true);

        CreateProductCommand command = mapper.toCommand(request);

        assertThat(command.name()).isEqualTo("Running Shoes");
        assertThat(command.description()).isEqualTo("Lightweight shoes");
        assertThat(command.brand()).isEqualTo("Nike");
        assertThat(command.category()).isEqualTo("Sports");
        assertThat(command.price()).isEqualByComparingTo("129.99");
        assertThat(command.stock()).isEqualTo(10);
        assertThat(command.active()).isTrue();
    }

    @Test
    void shouldMapUpdateRequestToCommand() {
        UpdateProductRequest request = new UpdateProductRequest("Running Shoes 2", "Improved shoes", "Nike", "Sports", 15, new BigDecimal("149.99"), true);

        UpdateProductCommand command = mapper.toCommand(request);

        assertThat(command.name()).isEqualTo("Running Shoes 2");
        assertThat(command.description()).isEqualTo("Improved shoes");
        assertThat(command.brand()).isEqualTo("Nike");
        assertThat(command.category()).isEqualTo("Sports");
        assertThat(command.stock()).isEqualTo(15);
        assertThat(command.price()).isEqualByComparingTo("149.99");
        assertThat(command.active()).isTrue();
    }

    @Test
    void shouldMapProductToResponse() {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight shoes", Brand.of("Nike"), Category.of("Sports"), new BigDecimal("129.99"), 10, true, 0L);

        GeneralProductResponse response = mapper.toResponse(product);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Running Shoes");
        assertThat(response.description()).isEqualTo("Lightweight shoes");
        assertThat(response.brand()).isEqualTo("Nike");
        assertThat(response.category()).isEqualTo("Sports");
        assertThat(response.stock()).isEqualTo(10);
        assertThat(response.price()).isEqualByComparingTo("129.99");
        assertThat(response.active()).isTrue();
    }
}
