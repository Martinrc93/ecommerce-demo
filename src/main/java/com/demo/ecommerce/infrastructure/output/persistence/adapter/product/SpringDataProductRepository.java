package com.demo.ecommerce.infrastructure.output.persistence.adapter.product;

import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByCategory(String category, Pageable pageable);
}
