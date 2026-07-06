package com.demo.ecommerce.domain;

import com.demo.ecommerce.domain.model.user.vo.Rol;
import com.demo.ecommerce.domain.model.user.vo.Rols;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RolTest {

    @Test
    void shouldCurrentlyBeAnEmptyWrapperAroundRols() {
        Field[] fields = Rol.class.getDeclaredFields();
        Method[] methods = Rol.class.getDeclaredMethods();

        assertEquals(1, fields.length);
        assertEquals("rol", fields[0].getName());
        assertEquals(Rols.class, fields[0].getType());
        assertTrue(Modifier.isPrivate(fields[0].getModifiers()));
        assertEquals(0, Arrays.stream(methods)
                .filter(method -> !method.isSynthetic())
                .count());
    }
}
