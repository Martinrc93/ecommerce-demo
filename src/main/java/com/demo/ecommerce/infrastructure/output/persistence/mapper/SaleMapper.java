package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Discount;
import com.demo.ecommerce.domain.shared.vo.Money;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleDetailEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "details", target = "saleDetails")
    @Mapping(source = "subTotal", target = "subtotal")
    Sale toDomain(SaleEntity entity);

    @Mapping(source = "saleDetails", target = "details")
    @Mapping(source = "subTotal", target = "subTotal", qualifiedByName = "moneyToBigDecimal")
    @Mapping(source = "discount", target = "discount", qualifiedByName = "discountToBigDecimal")
    @Mapping(source = "total", target = "total", qualifiedByName = "moneyToBigDecimal")
    SaleEntity toEntity(Sale domain);

    @Mapping(source = "quantity", target = "amount")
    SaleDetail toDomainDetail(SaleDetailEntity entity);

    @Mapping(source = "amount", target = "quantity")
    @Mapping(source = "price", target = "price", qualifiedByName = "moneyToBigDecimal")
    SaleDetailEntity toEntityDetail(SaleDetail domain);

    @Named("moneyToBigDecimal")
    default BigDecimal moneyToBigDecimal(Money money) {
        if (money == null) {
            return null;
        }
        return money.money();
    }

    default Money bigDecimalToMoney(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return Money.of(bigDecimal);
    }

    @Named("discountToBigDecimal")
    default BigDecimal discountToBigDecimal(Discount discount) {
        if (discount == null) {
            return null;
        }
        return discount.discount();
    }

    default Discount bigDecimalToDiscount(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return null;
        }
        return Discount.of(bigDecimal);
    }
}
