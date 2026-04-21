package com.demo.ecommerce.infrastructure.output.persistence.adapter.sale;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.application.port.out.SaleRepositoryPort;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import com.demo.ecommerce.infrastructure.output.persistence.mapper.SaleMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class SaleRepositoryAdapter implements SaleRepositoryPort {

    private final SpringDataSaleRepository repository;
    private final SaleMapper mapper;


    @Override
    public Sale save(Sale sale) {
        return mapper.toDomain(repository.save(mapper.toEntity(sale)));
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Sale findById(Long id) {
        Optional<SaleEntity> saleEntity = repository.findById(id);
        return saleEntity.map(mapper::toDomain).orElse(null);
    }
}
