package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.CreateSaleDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.SaleItemsDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.ItemsResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SaleDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleDtoMapper {

    CreateSaleCommand toCommand(CreateSaleDtoRequest dto);

    @Mapping(target = "reduceStock", ignore = true)
    Item toItemCommand(SaleItemsDtoRequest itemDto);

    @Mapping(target = "items", source = "saleDetails")
    @Mapping(target = "subTotal", source = "subTotal.money")
    @Mapping(target = "discount", source = "discount.discount")
    @Mapping(target = "total", source = "total.money")
    SaleDtoResponse toResponse(Sale sale);

    @Mapping(target = "quantity", source = "amount")
    @Mapping(target = "price", source = "price.money")
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "category", ignore = true)
    ItemsResponse toItemsResponse(SaleDetail saleDetail);
}