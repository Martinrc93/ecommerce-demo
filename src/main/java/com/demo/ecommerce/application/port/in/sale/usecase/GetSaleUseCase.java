package com.demo.ecommerce.application.port.in.sale.usecase;

import com.demo.ecommerce.domain.model.sale.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface GetSaleUseCase {
    Sale getById(Long id);
    Page<Sale> getByDates(LocalDateTime starDate, LocalDateTime endDate, Pageable pageable);
}