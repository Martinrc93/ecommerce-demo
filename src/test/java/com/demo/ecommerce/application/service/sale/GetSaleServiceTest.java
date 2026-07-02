package com.demo.ecommerce.application.service.sale;

import com.demo.ecommerce.application.port.out.SaleRepositoryPort;
import com.demo.ecommerce.domain.exception.sale.SaleNotFoundException;
import com.demo.ecommerce.domain.model.sale.Sale;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetSaleServiceTest {

    @Test
    void shouldReturnSaleByIdWhenItExists() {
        InMemorySaleRepository repository = new InMemorySaleRepository();
        Sale stored = Sale.create(UUID.randomUUID(), BigDecimal.ZERO);
        repository.byId = Optional.of(stored);
        GetSaleService getSaleService = new GetSaleService(repository);

        Sale sale = getSaleService.getById(7L);

        assertSame(stored, sale);
    }

    @Test
    void shouldThrowWhenSaleByIdDoesNotExist() {
        InMemorySaleRepository repository = new InMemorySaleRepository();
        GetSaleService getSaleService = new GetSaleService(repository);

        SaleNotFoundException exception = assertThrows(SaleNotFoundException.class,
                () -> getSaleService.getById(7L));

        assertEquals("Sale not found: 7", exception.getMessage());
    }

    @Test
    void shouldDelegateDateRangeListing() {
        InMemorySaleRepository repository = new InMemorySaleRepository();
        LocalDateTime start = LocalDateTime.of(2026, 1, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2026, 1, 31, 18, 0);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Sale> page = new PageImpl<>(List.of(Sale.create(UUID.randomUUID(), BigDecimal.ZERO)));
        repository.page = page;
        GetSaleService getSaleService = new GetSaleService(repository);

        Page<Sale> result = getSaleService.getByDates(start, end, pageable);

        assertSame(page, result);
        assertSame(start, repository.lastStart);
        assertSame(end, repository.lastEnd);
        assertSame(pageable, repository.lastPageable);
    }

    private static class InMemorySaleRepository implements SaleRepositoryPort {
        private Optional<Sale> byId = Optional.empty();
        private Page<Sale> page = Page.empty();
        private LocalDateTime lastStart;
        private LocalDateTime lastEnd;
        private Pageable lastPageable;

        @Override
        public Sale save(Sale sale) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Sale> findAll(Pageable pageable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Sale> findById(Long id) {
            return byId;
        }

        @Override
        public Page<Sale> findByDates(LocalDateTime starDate, LocalDateTime endDate, Pageable pageable) {
            this.lastStart = starDate;
            this.lastEnd = endDate;
            this.lastPageable = pageable;
            return page;
        }
    }
}
