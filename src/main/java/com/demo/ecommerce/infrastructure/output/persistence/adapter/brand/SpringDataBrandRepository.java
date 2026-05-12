package com.demo.ecommerce.infrastructure.output.persistence.adapter.brand;

import com.demo.ecommerce.infrastructure.output.persistence.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataBrandRepository extends JpaRepository<BrandEntity, Long> {
    Optional<BrandEntity> findByName(String name);
    Page<BrandEntity> findAll(Pageable pageable);
}
