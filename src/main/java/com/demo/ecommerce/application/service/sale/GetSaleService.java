package com.demo.ecommerce.application.service.sale;

import com.demo.ecommerce.application.port.in.sale.usecase.GetSaleUseCase;
import com.demo.ecommerce.application.port.out.SaleRepositoryPort;
import com.demo.ecommerce.domain.model.sale.Sale;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class GetSaleService implements GetSaleUseCase {

    private final SaleRepositoryPort repository;

    @Override
    public Sale getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Sale> getByDates(LocalDateTime starDate, LocalDateTime endDate, Pageable pageable) {


        return repository.findByDates(starDate,endDate,pageable);
    }
}