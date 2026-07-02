package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.model.product.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CreateBrandServiceTest {

    @Test
    void shouldSaveBrandCreatedFromCommandName() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        CreateBrandService createBrandService = new CreateBrandService(brandRepositoryPort);

        Brand brand = createBrandService.execute(new CreateBrandCommand("Lenovo"));

        assertNull(brand.id());
        assertEquals("Lenovo", brand.name());
        assertNotNull(brandRepositoryPort.savedBrand);
        assertEquals("Lenovo", brandRepositoryPort.savedBrand.name());
    }

    private static class InMemoryBrandRepository implements BrandRepositoryPort {
        private Brand savedBrand;

        @Override
        public Brand save(Brand brand) {
            this.savedBrand = brand;
            return brand;
        }

        @Override
        public Optional<Brand> findById(Long id) {
            throw new UnsupportedOperationException();
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
