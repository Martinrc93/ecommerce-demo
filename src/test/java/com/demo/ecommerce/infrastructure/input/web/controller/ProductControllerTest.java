package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.application.port.in.product.usecase.CreateProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.DeleteProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.GetProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.UpdateProductUseCase;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.CreateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.UpdateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.response.GeneralProductResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.ProductDtoMapper;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @MockBean
    private CreateProductUseCase createProductService;

    @MockBean
    private GetProductUseCase getProductService;

    @MockBean
    private UpdateProductUseCase updateProductService;

    @MockBean
    private DeleteProductUseCase deleteProductService;

    @MockBean
    private ProductDtoMapper productDtoMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldCreateProductAndReturnLocationHeader() throws Exception {
        CreateProductCommand command = new CreateProductCommand("Running Shoes", "Lightweight shoes", "Nike", "Sports", new BigDecimal("129.99"), 10, true);
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight shoes", Brand.of("Nike"), Category.of("Sports"), new BigDecimal("129.99"), 10, true, 0L);
        when(productDtoMapper.toCommand(any(CreateProductRequest.class))).thenReturn(command);
        when(createProductService.execute(command)).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  \"name\":\"Running Shoes\",
                                  \"description\":\"Lightweight shoes\",
                                  \"brand\":\"Nike\",
                                  \"category\":\"Sports\",
                                  \"price\":129.99,
                                  \"stock\":10,
                                  \"active\":true
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/products/1"));
    }

    @Test
    void shouldRejectInvalidCreatePayload() throws Exception {
        mockMvc.perform(post("/api/v1/products")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  \"name\":\"\",
                                  \"description\":\"short\",
                                  \"brand\":\"\",
                                  \"category\":\"\",
                                  \"price\":0,
                                  \"stock\":0,
                                  \"active\":true
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldFindProductById() throws Exception {
        Product product = Product.reconstitute(1L, "Running Shoes", "Lightweight shoes", Brand.of("Nike"), Category.of("Sports"), new BigDecimal("129.99"), 10, true, 0L);
        GeneralProductResponse response = new GeneralProductResponse(1L, "Running Shoes", "Lightweight shoes", "Nike", "Sports", 10, new BigDecimal("129.99"), true);
        when(getProductService.getById(1L)).thenReturn(product);
        when(productDtoMapper.toResponse(product)).thenReturn(response);

        mockMvc.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Running Shoes"));
    }

    @Test
    void shouldFindAllProducts() throws Exception {
        Product first = Product.reconstitute(1L, "Running Shoes", "Lightweight shoes", Brand.of("Nike"), Category.of("Sports"), new BigDecimal("129.99"), 10, true, 0L);
        Product second = Product.reconstitute(2L, "T-Shirt", "Cotton sports shirt", Brand.of("Adidas"), Category.of("Apparel"), new BigDecimal("39.99"), 20, true, 0L);
        when(getProductService.getAll(eq("Sports"), eq("Nike"), eq(new BigDecimal("10.00")), eq(new BigDecimal("200.00")), eq(true), any()))
                .thenReturn(new PageImpl<>(List.of(first, second)));
        when(productDtoMapper.toResponse(first)).thenReturn(new GeneralProductResponse(1L, "Running Shoes", "Lightweight shoes", "Nike", "Sports", 10, new BigDecimal("129.99"), true));
        when(productDtoMapper.toResponse(second)).thenReturn(new GeneralProductResponse(2L, "T-Shirt", "Cotton sports shirt", "Adidas", "Apparel", 20, new BigDecimal("39.99"), true));

        mockMvc.perform(get("/api/v1/products/all")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "asc")
                        .param("category", "Sports")
                        .param("brand", "Nike")
                        .param("minPrice", "10.00")
                        .param("maxPrice", "200.00")
                        .param("active", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        UpdateProductCommand command = new UpdateProductCommand("Running Shoes 2", "Improved shoes", "Nike", "Sports", 15, new BigDecimal("149.99"), true);
        Product product = Product.reconstitute(1L, "Running Shoes 2", "Improved shoes", Brand.of("Nike"), Category.of("Sports"), new BigDecimal("149.99"), 15, true, 0L);
        GeneralProductResponse response = new GeneralProductResponse(1L, "Running Shoes 2", "Improved shoes", "Nike", "Sports", 15, new BigDecimal("149.99"), true);
        when(productDtoMapper.toCommand(any(UpdateProductRequest.class))).thenReturn(command);
        when(updateProductService.update(eq(1L), eq(command))).thenReturn(product);
        when(productDtoMapper.toResponse(product)).thenReturn(response);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  \"name\":\"Running Shoes 2\",
                                  \"description\":\"Improved shoes\",
                                  \"brand\":\"Nike\",
                                  \"category\":\"Sports\",
                                  \"stock\":15,
                                  \"price\":149.99,
                                  \"active\":true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Running Shoes 2"));
    }

    @Test
    void shouldAcceptInvalidUpdatePayloadBecauseDtoHasNoValidationConstraints() throws Exception {
        UpdateProductCommand command = new UpdateProductCommand("", "", "", "", 0, BigDecimal.ZERO, true);
        Product product = Product.reconstitute(1L, "Fallback Name", "Fallback description", Brand.of("Nike"), Category.of("Sports"), new BigDecimal("149.99"), 15, true, 0L);
        GeneralProductResponse response = new GeneralProductResponse(1L, "Fallback Name", "Fallback description", "Nike", "Sports", 15, new BigDecimal("149.99"), true);
        when(productDtoMapper.toCommand(any(UpdateProductRequest.class))).thenReturn(command);
        when(updateProductService.update(eq(1L), eq(command))).thenReturn(product);
        when(productDtoMapper.toResponse(product)).thenReturn(response);

        mockMvc.perform(put("/api/v1/products/1")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  \"name\":\"\",
                                  \"description\":\"\",
                                  \"brand\":\"\",
                                  \"category\":\"\",
                                  \"stock\":0,
                                  \"price\":0,
                                  \"active\":true
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(deleteProductService).execute(1L);

        mockMvc.perform(delete("/api/v1/products/1"))
                .andExpect(status().isNoContent());
    }
}
