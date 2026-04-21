package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "details", target = "saleDetails")
    Sale toDomain(SaleEntity entity);

    @Mapping(source = "saleDetails", target = "details")
    SaleEntity toEntity(Sale domain);

}
