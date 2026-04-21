package com.demo.ecommerce.infrastructure.input.web.mapper.product;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.CreateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.UpdateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.response.GeneralProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    CreateProductCommand toCommand(CreateProductRequest request);

    UpdateProductCommand toCommand(UpdateProductRequest request);

    @Mapping(source = "productDetail.name", target = "name")
    @Mapping(source = "productDetail.description", target = "description")
    @Mapping(source = "productDetail.brand", target = "brand")
    @Mapping(source = "productDetail.category", target = "category")
    @Mapping(source = "price.money", target = "price")
    @Mapping(source = "productAvailability.stock.stock", target = "stock")
    @Mapping(source = "productAvailability.active", target = "active")
    GeneralProductResponse toResponse(Product product);
}
