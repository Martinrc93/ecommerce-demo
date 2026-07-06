package com.demo.ecommerce.infrastructure.input.web.mapper;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Money;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.CreateSaleDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.SaleItemsDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.ItemsResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SaleDtoResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaleDtoMapperTest {

    private final SaleDtoMapper mapper = Mappers.getMapper(SaleDtoMapper.class);

    @Test
    void shouldMapCreateDtoToCommandIgnoringDiscountField() {
        UUID userId = UUID.randomUUID();
        CreateSaleDtoRequest request = new CreateSaleDtoRequest(userId, "10.00", List.of(new SaleItemsDtoRequest(1L, 2)));

        CreateSaleCommand command = mapper.toCommand(request);

        assertThat(command.userId()).isEqualTo(userId);
        assertThat(command.items()).hasSize(1);
        assertThat(command.items().get(0).productId()).isEqualTo(1L);
        assertThat(command.items().get(0).quantity()).isEqualTo(2);
        assertThat(command.items().get(0).reduceStock()).isNull();
    }

    @Test
    void shouldMapSaleItemToCommandIgnoringReduceStock() {
        SaleItemsDtoRequest request = new SaleItemsDtoRequest(1L, 2);

        Item item = mapper.toItemCommand(request);

        assertThat(item.productId()).isEqualTo(1L);
        assertThat(item.quantity()).isEqualTo(2);
        assertThat(item.reduceStock()).isNull();
    }

    @Test
    void shouldMapSaleToResponse() {
        Sale sale = Sale.reconstitute(
                1L,
                UUID.randomUUID(),
                List.of(SaleDetail.reconstitute(1L, 1L, 10L, "Running Shoes", "Nike", "Sports", 2, Money.of(new BigDecimal("129.99")), BigDecimal.ZERO)),
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98")
        );

        SaleDtoResponse response = mapper.toResponse(sale);

        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.items()).hasSize(1);
        ItemsResponse item = response.items().get(0);
        assertThat(item.name()).isEqualTo("Running Shoes");
        assertThat(item.brand()).isEqualTo("Nike");
        assertThat(item.category()).isEqualTo("Sports");
        assertThat(item.quantity()).isEqualTo(2);
        assertThat(item.price()).isEqualByComparingTo("129.99");
        assertThat(response.subTotal()).isEqualByComparingTo("259.98");
        assertThat(response.discount()).isEqualByComparingTo("0");
        assertThat(response.total()).isEqualByComparingTo("259.98");
    }
}
