package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteBrandServiceTest {

    @Test
    void shouldDeleteBrandWhenItExists() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        brandRepositoryPort.storedBrand = Brand.of(7L, "Lenovo");
        DeleteBrandService deleteBrandService = new DeleteBrandService(brandRepositoryPort);

        deleteBrandService.execute(7L);

        assertTrue(brandRepositoryPort.deleted);
        assertEquals(7L, brandRepositoryPort.deletedId);
    }

    @Test
    void shouldThrowWhenBrandDoesNotExist() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        DeleteBrandService deleteBrandService = new DeleteBrandService(brandRepositoryPort);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> deleteBrandService.execute(7L));

        assertEquals("Brand not found with id: 7", exception.getMessage());
    }

    private static class InMemoryBrandRepository implements BrandRepositoryPort {
        private Brand storedBrand;
        private boolean deleted;
        private Long deletedId;

        @Override
        public Brand save(Brand brand) {
            this.storedBrand = brand;
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
            this.deleted = true;
            this.deletedId = id;
        }
    }
}
