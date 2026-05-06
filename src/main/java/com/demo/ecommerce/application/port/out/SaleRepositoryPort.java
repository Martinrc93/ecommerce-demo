package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.domain.model.sale.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;


public interface SaleRepositoryPort {

    Sale save (Sale sale);
    Page<Sale> findAll (Pageable pageable);
    Optional<Sale> findById (Long id);
    Page<Sale> findByDates (LocalDateTime starDate, LocalDateTime endDate,Pageable pageable);

}
