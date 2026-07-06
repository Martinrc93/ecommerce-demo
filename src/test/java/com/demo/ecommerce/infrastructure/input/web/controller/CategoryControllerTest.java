package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.application.port.in.category.usecase.CreateCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.DeleteCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.GetCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.UpdateCategoryUseCase;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.CategoryDtoMapper;
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

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetCategoryUseCase getCategoryUseCase;

    @MockBean
    private UpdateCategoryUseCase updateCategoryUseCase;

    @MockBean
    private DeleteCategoryUseCase deleteCategoryUseCase;

    @MockBean
    private CategoryDtoMapper categoryDtoMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldCreateCategory() throws Exception {
        CreateCategoryCommand command = new CreateCategoryCommand("Shoes");
        Category category = Category.of(1L, "Shoes");
        when(categoryDtoMapper.toCommand(any())).thenReturn(command);
        when(createCategoryUseCase.execute(command)).thenReturn(category);
        when(categoryDtoMapper.toResponse(category)).thenReturn(new CategoryDtoResponse(1L, "Shoes"));

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Shoes\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Shoes"));
    }

    @Test
    void shouldFindCategoryById() throws Exception {
        Category category = Category.of(1L, "Shoes");
        when(getCategoryUseCase.getById(1L)).thenReturn(category);
        when(categoryDtoMapper.toResponse(category)).thenReturn(new CategoryDtoResponse(1L, "Shoes"));

        mockMvc.perform(get("/api/v1/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Shoes"));
    }

    @Test
    void shouldFindAllCategories() throws Exception {
        Category shoes = Category.of(1L, "Shoes");
        Category shirts = Category.of(2L, "Shirts");
        when(getCategoryUseCase.getAll(any())).thenReturn(new PageImpl<>(List.of(shoes, shirts)));
        when(categoryDtoMapper.toResponse(shoes)).thenReturn(new CategoryDtoResponse(1L, "Shoes"));
        when(categoryDtoMapper.toResponse(shirts)).thenReturn(new CategoryDtoResponse(2L, "Shirts"));

        mockMvc.perform(get("/api/v1/categories/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("sortDirection", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Shoes"))
                .andExpect(jsonPath("$.content[1].name").value("Shirts"));
    }

    @Test
    void shouldFindCategoryByName() throws Exception {
        Category category = Category.of(1L, "Shoes");
        when(getCategoryUseCase.getByName("Shoes")).thenReturn(category);
        when(categoryDtoMapper.toResponse(category)).thenReturn(new CategoryDtoResponse(1L, "Shoes"));

        mockMvc.perform(get("/api/v1/categories/by-name/Shoes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Shoes"));
    }

    @Test
    void shouldUpdateCategory() throws Exception {
        CreateCategoryCommand command = new CreateCategoryCommand("Shirts");
        Category category = Category.of(1L, "Shirts");
        when(categoryDtoMapper.toCommand(any())).thenReturn(command);
        when(updateCategoryUseCase.execute(eq(1L), eq(command))).thenReturn(category);
        when(categoryDtoMapper.toResponse(category)).thenReturn(new CategoryDtoResponse(1L, "Shirts"));

        mockMvc.perform(put("/api/v1/categories/1")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"Shirts\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Shirts"));
    }

    @Test
    void shouldDeleteCategory() throws Exception {
        doNothing().when(deleteCategoryUseCase).execute(1L);

        mockMvc.perform(delete("/api/v1/categories/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldAcceptInvalidCategoryPayloadBecauseValidationIsMissing() throws Exception {
        CreateCategoryCommand command = new CreateCategoryCommand("");
        Category category = Category.of(1L, "Fallback");
        when(categoryDtoMapper.toCommand(any())).thenReturn(command);
        when(createCategoryUseCase.execute(command)).thenReturn(category);
        when(categoryDtoMapper.toResponse(category)).thenReturn(new CategoryDtoResponse(1L, "Fallback"));

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isOk());
    }
}
