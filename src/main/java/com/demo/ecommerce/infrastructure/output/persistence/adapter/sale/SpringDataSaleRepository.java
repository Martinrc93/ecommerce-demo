package com.demo.ecommerce.infrastructure.output.persistence.adapter.sale;

import com.demo.ecommerce.infrastructure.output.persistence.entity.SaleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface SpringDataSaleRepository extends JpaRepository<SaleEntity, Long> {

    @Query("""
    SELECT s FROM SaleEntity s
    WHERE s.date BETWEEN :startDate AND :endDate
    """)
    Page<SaleEntity> findByDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
