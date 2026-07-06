package com.demo.ecommerce.infrastructure.output.persistence.adapter.sale;

import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Money;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleDetailEntity;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.SaleMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaleRepositoryAdapterTest {

    @Mock
    private SpringDataSaleRepository repository;

    @Mock
    private SaleMapper mapper;

    @InjectMocks
    private SaleRepositoryAdapter adapter;

    @Test
    void shouldSaveSale() {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(1L, userId, List.of(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        SaleEntity entity = new SaleEntity(1L, userId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), List.of());

        when(mapper.toEntity(sale)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(sale);

        Sale result = adapter.save(sale);

        assertThat(result).isEqualTo(sale);
    }

    @Test
    void shouldFindAll() {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(1L, userId, List.of(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        SaleEntity entity = new SaleEntity(1L, userId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), List.of());
        PageRequest pageable = PageRequest.of(0, 10);

        when(repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(mapper.toDomain(entity)).thenReturn(sale);

        Page<Sale> result = adapter.findAll(pageable);

        assertThat(result.getContent()).containsExactly(sale);
    }

    @Test
    void shouldFindById() {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(1L, userId, List.of(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        SaleEntity entity = new SaleEntity(1L, userId, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, LocalDateTime.now(), List.of());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(sale);

        Optional<Sale> result = adapter.findById(1L);

        assertThat(result).contains(sale);
    }

    @Test
    void shouldFindByDates() {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(
                1L,
                userId,
                List.of(SaleDetail.reconstitute(1L, 1L, 10L, "Running Shoes", "Nike", "Sports", 2, Money.of(new BigDecimal("129.99")), BigDecimal.ZERO)),
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98")
        );
        SaleEntity entity = new SaleEntity(
                1L,
                userId,
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98"),
                LocalDateTime.now(),
                List.of(new SaleDetailEntity(1L, 1L, 10L, "Running Shoes", "Nike", "Sports", 2, new BigDecimal("129.99"), BigDecimal.ZERO))
        );
        PageRequest pageable = PageRequest.of(0, 10);
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(2026, 7, 3, 23, 59);

        when(repository.findByDateBetween(start, end, pageable)).thenReturn(new PageImpl<>(List.of(entity), pageable, 1));
        when(mapper.toDomain(entity)).thenReturn(sale);

        Page<Sale> result = adapter.findByDates(start, end, pageable);

        assertThat(result.getContent()).containsExactly(sale);
    }
}
