package com.demo.ecommerce.service.brand;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.application.service.brand.CreateBrandService;
import com.demo.ecommerce.domain.model.product.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateBrandServiceTest {

    @Mock
    private BrandRepositoryPort brandRepositoryPort;

    @InjectMocks
    private CreateBrandService createBrandService;

    @Test
    void shouldCreateBrandSuccessfully() {
        // Given
        CreateBrandCommand command = new CreateBrandCommand("Nike");
        Brand expected = Brand.of(null, "Nike");

        when(brandRepositoryPort.save(any(Brand.class))).thenReturn(expected);

        // When
        Brand result = createBrandService.execute(command);

        // Then
        assertNotNull(result);
        assertEquals("Nike", result.name());
        verify(brandRepositoryPort, times(1)).save(any(Brand.class));
    }

    @Test
    void shouldCallRepositorySaveOnce() {
        // Given
        CreateBrandCommand command = new CreateBrandCommand("Adidas");
        when(brandRepositoryPort.save(any(Brand.class))).thenReturn(Brand.of(null, "Adidas"));

        // When
        createBrandService.execute(command);

        // Then
        verify(brandRepositoryPort, times(1)).save(any(Brand.class));
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> createBrandService.execute(new CreateBrandCommand("")));

        // el repositorio nunca se llama si el dominio falla
        verify(brandRepositoryPort, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenNameIsTooShort() {
        assertThrows(IllegalArgumentException.class,
                () -> createBrandService.execute(new CreateBrandCommand("AB")));

        verify(brandRepositoryPort, never()).save(any());
    }
}