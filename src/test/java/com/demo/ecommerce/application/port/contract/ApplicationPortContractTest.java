package com.demo.ecommerce.application.port.contract;

import com.demo.ecommerce.application.port.in.sale.command.UpdateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.usecase.UpdateSaleUseCase;
import com.demo.ecommerce.application.port.out.AuthRepositoryPort;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.application.port.out.CategoryRepositoryPort;
import com.demo.ecommerce.application.port.out.ProductRepositoryPort;
import com.demo.ecommerce.application.port.out.SaleRepositoryPort;
import com.demo.ecommerce.application.port.out.UserRepositoryPort;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationPortContractTest {

    @Test
    void shouldExposeExpectedOutPortMethodCounts() {
        Map<Class<?>, Integer> expected = Map.of(
                AuthRepositoryPort.class, 4,
                BrandRepositoryPort.class, 5,
                CategoryRepositoryPort.class, 5,
                ProductRepositoryPort.class, 9,
                SaleRepositoryPort.class, 4,
                UserRepositoryPort.class, 3
        );

        expected.forEach((type, count) -> assertEquals(count.longValue(), declaredMethodCount(type), type.getSimpleName()));
    }

    @Test
    void shouldConfirmUpdateSaleContractsAreCurrentlyEmpty() {
        assertEquals(0, UpdateSaleUseCase.class.getDeclaredMethods().length);
        assertEquals(0, UpdateSaleCommand.class.getRecordComponents().length);
    }

    @Test
    void shouldKeepOutPortsAsPureInterfaces() {
        assertTrue(AuthRepositoryPort.class.isInterface());
        assertTrue(BrandRepositoryPort.class.isInterface());
        assertTrue(CategoryRepositoryPort.class.isInterface());
        assertTrue(ProductRepositoryPort.class.isInterface());
        assertTrue(SaleRepositoryPort.class.isInterface());
        assertTrue(UserRepositoryPort.class.isInterface());
    }

    private static long declaredMethodCount(Class<?> type) {
        return Arrays.stream(type.getDeclaredMethods())
                .map(Method::getName)
                .distinct()
                .count();
    }
}
