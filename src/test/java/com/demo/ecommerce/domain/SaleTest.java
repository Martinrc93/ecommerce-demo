package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    @Test
    void shouldCreateSaleWithZeroTotalsAndConfiguredDiscount() {
        UUID userId = UUID.randomUUID();

        Sale sale = Sale.create(userId, new BigDecimal("10"));

        assertNull(sale.getId());
        assertEquals(userId, sale.getUserId());
        assertNotNull(sale.getSaleDetails());
        assertTrue(sale.getSaleDetails().isEmpty());
        assertEquals(new BigDecimal("0.00"), sale.getSubTotal().money());
        assertEquals(new BigDecimal("0.10"), sale.getDiscount().discount());
        assertEquals(new BigDecimal("0.00"), sale.getTotal().money());
    }

    @Test
    void shouldAccumulateSubtotalAndApplySaleDiscountWhenAddingDetails() {
        Sale sale = Sale.create(UUID.randomUUID(), new BigDecimal("10"));
        SaleDetail firstDetail = SaleDetail.create(1L, "Laptop", "Lenovo", "Tech", 2, Money.of(new BigDecimal("10.00")), new BigDecimal("99"));
        SaleDetail secondDetail = SaleDetail.create(2L, "Mouse", "Logitech", "Tech", 1, Money.of(new BigDecimal("5.50")), BigDecimal.ZERO);

        Sale updated = sale.addSaleDetail(firstDetail).addSaleDetail(secondDetail);

        assertSame(sale, updated);
        assertEquals(List.of(firstDetail, secondDetail), updated.getSaleDetails());
        assertEquals(new BigDecimal("25.50"), updated.getSubTotal().money());
        assertEquals(new BigDecimal("22.95"), updated.getTotal().money());
    }

    @Test
    void shouldReconstituteSaleUsingProvidedSnapshotValues() {
        SaleDetail detail = SaleDetail.reconstitute(
                1L,
                2L,
                3L,
                "Laptop",
                "Lenovo",
                "Tech",
                1,
                Money.of(new BigDecimal("50.00")),
                BigDecimal.ZERO
        );

        Sale sale = Sale.reconstitute(
                10L,
                UUID.randomUUID(),
                List.of(detail),
                new BigDecimal("50.00"),
                new BigDecimal("15"),
                new BigDecimal("42.50")
        );

        assertEquals(10L, sale.getId());
        assertEquals(1, sale.getSaleDetails().size());
        assertEquals(new BigDecimal("50.00"), sale.getSubTotal().money());
        assertEquals(new BigDecimal("0.15"), sale.getDiscount().discount());
        assertEquals(new BigDecimal("42.50"), sale.getTotal().money());
    }
}
