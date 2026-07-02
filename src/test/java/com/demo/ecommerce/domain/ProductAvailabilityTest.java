package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.product.ProductAvailability;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductAvailabilityTest {

    @Test
    void shouldCreateAvailabilityWithStockAndActiveFlag() {
        ProductAvailability availability = ProductAvailability.of(10, true);

        assertEquals(10, availability.stock().stock());
        assertTrue(availability.active());
    }

    @Test
    void shouldUpdateStockWithoutChangingActiveFlag() {
        ProductAvailability availability = ProductAvailability.of(10, false);

        ProductAvailability updated = availability.updateStock(4);

        assertEquals(6, updated.stock().stock());
        assertFalse(updated.active());
    }
}
