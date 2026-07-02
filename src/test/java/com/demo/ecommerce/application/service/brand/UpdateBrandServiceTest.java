package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UpdateBrandServiceTest {

    @Test
    void shouldUpdateBrandNamePreservingId() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        brandRepositoryPort.storedBrand = Brand.of(7L, "Lenovo");
        UpdateBrandService updateBrandService = new UpdateBrandService(brandRepositoryPort);

        Brand updated = updateBrandService.execute(7L, new CreateBrandCommand("Logitech"));

        assertEquals(7L, updated.id());
        assertEquals("Logitech", updated.name());
        assertEquals(7L, brandRepositoryPort.savedBrand.id());
        assertEquals("Logitech", brandRepositoryPort.savedBrand.name());
    }

    @Test
    void shouldThrowWhenBrandDoesNotExist() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        UpdateBrandService updateBrandService = new UpdateBrandService(brandRepositoryPort);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> updateBrandService.execute(7L, new CreateBrandCommand("Logitech")));

        assertEquals("Brand not found with id: 7", exception.getMessage());
        assertNull(brandRepositoryPort.savedBrand);
    }

    private static class InMemoryBrandRepository implements BrandRepositoryPort {
        private Brand storedBrand;
        private Brand savedBrand;

        @Override
        public Brand save(Brand brand) {
            this.savedBrand = brand;
            return brand;
        }

        @Override
        public Optional<Brand> findById(Long id) {
            return storedBrand != null && storedBrand.id().equals(id)
                    ? Optional.of(storedBrand)
                    : Optional.empty();
        }

        @Override
        public Optional<Brand> findByName(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Page<Brand> getAll(Pageable pageable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }
}
