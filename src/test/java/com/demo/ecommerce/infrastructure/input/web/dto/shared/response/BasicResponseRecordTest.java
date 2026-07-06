package com.demo.ecommerce.infrastructure.input.web.dto.shared.response;

import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.user.response.UserDtoResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicResponseRecordTest {

    @Test
    void shouldExposeBrandResponseFields() {
        BrandDtoResponse response = new BrandDtoResponse("Nike");

        assertEquals("Nike", response.name());
    }

    @Test
    void shouldExposeCategoryResponseFields() {
        CategoryDtoResponse response = new CategoryDtoResponse(1L, "Footwear");

        assertEquals(1L, response.id());
        assertEquals("Footwear", response.name());
    }

    @Test
    void shouldExposeUserResponseFields() {
        UserDtoResponse response = new UserDtoResponse("John", "Perez", "john.perez@example.com");

        assertEquals("John", response.name());
        assertEquals("Perez", response.lastName());
        assertEquals("john.perez@example.com", response.email());
    }
}
