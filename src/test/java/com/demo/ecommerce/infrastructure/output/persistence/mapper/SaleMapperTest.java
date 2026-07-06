package com.demo.ecommerce.infrastructure.output.persistence.mapper;

import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Money;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleDetailEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaleMapperTest {

    private final SaleMapper mapper = Mappers.getMapper(SaleMapper.class);

    @Test
    void shouldMapEntityToDomain() {
        UUID userId = UUID.randomUUID();
        SaleEntity entity = new SaleEntity(
                1L,
                userId,
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98"),
                LocalDateTime.now(),
                List.of(new SaleDetailEntity(1L, 1L, 10L, "Running Shoes", "Nike", "Sports", 2, new BigDecimal("129.99"), BigDecimal.ZERO))
        );

        Sale sale = mapper.toDomain(entity);

        assertThat(sale.getId()).isEqualTo(1L);
        assertThat(sale.getUserId()).isEqualTo(userId);
        assertThat(sale.getSubTotal().money()).isEqualByComparingTo("259.98");
        assertThat(sale.getDiscount().discount()).isEqualByComparingTo("0.00");
        assertThat(sale.getTotal().money()).isEqualByComparingTo("259.98");
        assertThat(sale.getSaleDetails()).hasSize(1);
        assertThat(sale.getSaleDetails().get(0).getAmount()).isEqualTo(2);
    }

    @Test
    void shouldMapDomainToEntity() {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(
                1L,
                userId,
                List.of(SaleDetail.reconstitute(1L, 1L, 10L, "Running Shoes", "Nike", "Sports", 2, Money.of(new BigDecimal("129.99")), BigDecimal.ZERO)),
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98")
        );

        SaleEntity entity = mapper.toEntity(sale);

        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getUserId()).isEqualTo(userId);
        assertThat(entity.getSubTotal()).isEqualByComparingTo("259.98");
        assertThat(entity.getDiscount()).isEqualByComparingTo("0.00");
        assertThat(entity.getTotal()).isEqualByComparingTo("259.98");
        assertThat(entity.getDetails()).hasSize(1);
        assertThat(entity.getDetails().get(0).getQuantity()).isEqualTo(2);
    }
}
