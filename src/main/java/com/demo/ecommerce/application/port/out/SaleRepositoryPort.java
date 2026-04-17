package com.demo.ecommerce.application.port.out;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.domain.model.sale.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SaleRepositoryPort {
    Sale save (CreateSaleCommand command);
    Page<Sale> findAll (Pageable pageable);
}
