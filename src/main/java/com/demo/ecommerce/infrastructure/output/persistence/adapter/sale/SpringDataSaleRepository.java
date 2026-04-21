package com.demo.ecommerce.infrastructure.output.persistence.adapter.sale;

import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataSaleRepository extends JpaRepository<SaleEntity, Long> {
}
