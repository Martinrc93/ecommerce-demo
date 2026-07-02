package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.user.vo.Rols;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RolsTest {

    @Test
    void shouldExposeCurrentRoleConstants() {
        assertArrayEquals(new Rols[]{Rols.BUYER, Rols.ADMIN}, Rols.values());
        assertEquals(Rols.BUYER, Rols.valueOf("BUYER"));
        assertEquals(Rols.ADMIN, Rols.valueOf("ADMIN"));
    }
}
