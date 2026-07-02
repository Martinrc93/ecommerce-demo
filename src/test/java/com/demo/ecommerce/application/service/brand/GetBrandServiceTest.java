package com.demo.ecommerce.application.service.brand;

import com.demo.ecommerce.application.port.out.BrandRepositoryPort;
import com.demo.ecommerce.domain.exception.global.NotFoundException;
import com.demo.ecommerce.domain.model.product.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetBrandServiceTest {

    @Test
    void shouldReturnBrandByIdWhenItExists() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        Brand stored = Brand.of(7L, "Lenovo");
        brandRepositoryPort.byId = Optional.of(stored);
        GetBrandService getBrandService = new GetBrandService(brandRepositoryPort);

        Brand brand = getBrandService.getById(7L);

        assertSame(stored, brand);
    }

    @Test
    void shouldThrowWhenBrandByIdDoesNotExist() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        GetBrandService getBrandService = new GetBrandService(brandRepositoryPort);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> getBrandService.getById(7L));

        assertEquals("Brand not found with id: 7", exception.getMessage());
    }

    @Test
    void shouldReturnBrandByNameWhenItExists() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        Brand stored = Brand.of(7L, "Lenovo");
        brandRepositoryPort.byName = Optional.of(stored);
        GetBrandService getBrandService = new GetBrandService(brandRepositoryPort);

        Brand brand = getBrandService.getByName("Lenovo");

        assertSame(stored, brand);
    }

    @Test
    void shouldThrowWhenBrandByNameDoesNotExist() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        GetBrandService getBrandService = new GetBrandService(brandRepositoryPort);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> getBrandService.getByName("Lenovo"));

        assertEquals("Brand not found with name: Lenovo", exception.getMessage());
    }

    @Test
    void shouldDelegatePagedListing() {
        InMemoryBrandRepository brandRepositoryPort = new InMemoryBrandRepository();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Brand> page = new PageImpl<>(List.of(Brand.of(7L, "Lenovo")));
        brandRepositoryPort.page = page;
        GetBrandService getBrandService = new GetBrandService(brandRepositoryPort);

        Page<Brand> result = getBrandService.getAll(pageable);

        assertSame(page, result);
        assertSame(pageable, brandRepositoryPort.lastPageable);
    }

    private static class InMemoryBrandRepository implements BrandRepositoryPort {
        private Optional<Brand> byId = Optional.empty();
        private Optional<Brand> byName = Optional.empty();
        private Page<Brand> page = Page.empty();
        private Pageable lastPageable;

        @Override
        public Brand save(Brand brand) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Optional<Brand> findById(Long id) {
            return byId;
        }

        @Override
        public Optional<Brand> findByName(String name) {
            return byName;
        }

        @Override
        public Page<Brand> getAll(Pageable pageable) {
            this.lastPageable = pageable;
            return page;
        }

        @Override
        public void deleteById(Long id) {
            throw new UnsupportedOperationException();
        }
    }
}
