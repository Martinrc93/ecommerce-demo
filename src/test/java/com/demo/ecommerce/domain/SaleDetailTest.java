package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SaleDetailTest {

    @Test
    void shouldCreateSaleDetailWithProvidedValues() {
        SaleDetail saleDetail = SaleDetail.create(
                3L,
                "Laptop",
                "Lenovo",
                "Tech",
                2,
                Money.of(new BigDecimal("10.50")),
                new BigDecimal("15")
        );

        assertNull(saleDetail.getId());
        assertNull(saleDetail.getSaleId());
        assertEquals(3L, saleDetail.getProductId());
        assertEquals("Laptop", saleDetail.getProductName());
        assertEquals("Lenovo", saleDetail.getBrandName());
        assertEquals("Tech", saleDetail.getCategoryName());
        assertEquals(2, saleDetail.getAmount());
        assertEquals(new BigDecimal("10.50"), saleDetail.getPrice().money());
        assertEquals(new BigDecimal("15"), saleDetail.getDiscount());
    }

    @Test
    void shouldReconstituteSaleDetailUsingSnapshotValues() {
        SaleDetail saleDetail = SaleDetail.reconstitute(
                1L,
                2L,
                3L,
                "Laptop",
                "Lenovo",
                "Tech",
                4,
                Money.of(new BigDecimal("99.99")),
                BigDecimal.ZERO
        );

        assertEquals(1L, saleDetail.getId());
        assertEquals(2L, saleDetail.getSaleId());
        assertEquals(3L, saleDetail.getProductId());
        assertEquals("Laptop", saleDetail.getProductName());
        assertEquals("Lenovo", saleDetail.getBrandName());
        assertEquals("Tech", saleDetail.getCategoryName());
        assertEquals(4, saleDetail.getAmount());
        assertEquals(new BigDecimal("99.99"), saleDetail.getPrice().money());
        assertEquals(BigDecimal.ZERO, saleDetail.getDiscount());
    }
}
