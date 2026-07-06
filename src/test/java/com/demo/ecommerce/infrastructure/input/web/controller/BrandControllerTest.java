package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.in.brand.usecase.CreateBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.DeleteBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.GetBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.UpdateBrandUseCase;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.BrandDtoMapper;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
class BrandControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @MockBean
    private CreateBrandUseCase createBrandService;

    @MockBean
    private GetBrandUseCase getBrandService;

    @MockBean
    private UpdateBrandUseCase updateBrandService;

    @MockBean
    private DeleteBrandUseCase deleteBrandService;

    @MockBean
    private BrandDtoMapper brandDtoMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldCreateBrand() throws Exception {
        Brand brand = Brand.of(1L, "Nike");
        when(createBrandService.execute(any(CreateBrandCommand.class))).thenReturn(brand);
        when(brandDtoMapper.toResponse(brand)).thenReturn(new BrandDtoResponse("Nike"));

        mockMvc.perform(post("/api/v1/brands")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Nike\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nike"));
    }

    @Test
    void shouldFindBrandById() throws Exception {
        Brand brand = Brand.of(1L, "Nike");
        when(getBrandService.getById(1L)).thenReturn(brand);
        when(brandDtoMapper.toResponse(brand)).thenReturn(new BrandDtoResponse("Nike"));

        mockMvc.perform(get("/api/v1/brands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nike"));
    }

    @Test
    void shouldFindBrandByName() throws Exception {
        Brand brand = Brand.of(1L, "Nike");
        when(getBrandService.getByName("Nike")).thenReturn(brand);
        when(brandDtoMapper.toResponse(brand)).thenReturn(new BrandDtoResponse("Nike"));

        mockMvc.perform(get("/api/v1/brands/by-name/Nike"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nike"));
    }

    @Test
    void shouldFindAllBrands() throws Exception {
        Brand nike = Brand.of(1L, "Nike");
        Brand adidas = Brand.of(2L, "Adidas");
        when(getBrandService.getAll(any())).thenReturn(new PageImpl<>(List.of(nike, adidas)));
        when(brandDtoMapper.toResponse(nike)).thenReturn(new BrandDtoResponse("Nike"));
        when(brandDtoMapper.toResponse(adidas)).thenReturn(new BrandDtoResponse("Adidas"));

        mockMvc.perform(get("/api/v1/brands/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("sortDirection", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Nike"))
                .andExpect(jsonPath("$.content[1].name").value("Adidas"));
    }

    @Test
    void shouldUpdateBrand() throws Exception {
        CreateBrandCommand command = new CreateBrandCommand("Puma");
        Brand brand = Brand.of(1L, "Puma");
        when(brandDtoMapper.toCommand(any())).thenReturn(command);
        when(updateBrandService.execute(eq(1L), eq(command))).thenReturn(brand);
        when(brandDtoMapper.toResponse(brand)).thenReturn(new BrandDtoResponse("Puma"));

        mockMvc.perform(put("/api/v1/brands/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Puma\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Puma"));
    }

    @Test
    void shouldDeleteBrand() throws Exception {
        doNothing().when(deleteBrandService).execute(1L);

        mockMvc.perform(delete("/api/v1/brands/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAcceptInvalidBrandPayloadBecauseValidationIsMissing() throws Exception {
        Brand brand = Brand.of(1L, "Nike");
        when(createBrandService.execute(any(CreateBrandCommand.class))).thenReturn(brand);
        when(brandDtoMapper.toResponse(brand)).thenReturn(new BrandDtoResponse("Nike"));

        mockMvc.perform(post("/api/v1/brands")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isOk());
    }
}
