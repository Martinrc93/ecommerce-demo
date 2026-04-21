package com.demo.ecommerce.infrastructure.input.web.mapper.product;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.CreateSaleDtoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleDtoMapper {

    CreateSaleCommand toCommand(CreateSaleDtoRequest dto);

}
