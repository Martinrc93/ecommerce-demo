package com.demo.ecommerce.infrastructure.output.persistence.adapter.product;

import com.demo.ecommerce.infrastructure.output.persistence.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.stock <= :stock",
           countQuery = "SELECT count(p) FROM ProductEntity p WHERE p.stock <= :stock")
    Page<ProductEntity> findByStock(@Param("stock") Integer stock, Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity p WHERE " +
            "(:category IS NULL OR p.category.name = :category) AND " +
            "(:brand IS NULL OR p.brand.name = :brand) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:active IS NULL OR p.active = :active)",
           countQuery = "SELECT count(p) FROM ProductEntity p WHERE " +
            "(:category IS NULL OR p.category.name = :category) AND " +
            "(:brand IS NULL OR p.brand.name = :brand) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:active IS NULL OR p.active = :active)")
    Page<ProductEntity> findByFilters(@Param("category") String category, 
                                      @Param("brand") String brand,
                                      @Param("minPrice") BigDecimal minPrice,
                                      @Param("maxPrice") BigDecimal maxPrice,
                                      @Param("active") Boolean active,
                                      Pageable pageable);

    Optional<ProductEntity> findByNameContainingIgnoreCase(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p WHERE p.id IN :ids")
    List<ProductEntity> findAllByIdsWithPessimisticLock(@Param("ids") List<Long> ids);

}
