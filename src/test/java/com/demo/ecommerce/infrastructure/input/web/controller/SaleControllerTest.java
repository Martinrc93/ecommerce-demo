package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.sale.command.CreateSaleCommand;
import com.demo.ecommerce.application.port.in.sale.command.Item;
import com.demo.ecommerce.application.port.in.sale.usecase.CreateSaleUseCase;
import com.demo.ecommerce.application.port.in.sale.usecase.GetSaleUseCase;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.domain.model.saledetail.SaleDetail;
import com.demo.ecommerce.domain.shared.vo.Money;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.ItemsResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SaleDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.SaleDtoMapper;
import com.demo.ecommerce.infrastructure.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SaleController.class)
@AutoConfigureMockMvc(addFilters = false)
class SaleControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mockMvc;

    @MockBean
    private CreateSaleUseCase createSaleService;

    @MockBean
    private GetSaleUseCase getSaleService;

    @MockBean
    private SaleDtoMapper mapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void shouldCreateSale() throws Exception {
        UUID userId = UUID.randomUUID();
        CreateSaleCommand command = new CreateSaleCommand(userId, List.of(new Item(1L, 2, null)));
        Sale sale = Sale.reconstitute(1L, userId, List.of(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        when(mapper.toCommand(any())).thenReturn(command);
        when(createSaleService.save(command)).thenReturn(sale);

        mockMvc.perform(post("/api/v1/sales")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  \"userId\":\"%s\",
                                  \"Discount\":\"10.00\",
                                  \"items\":[{"productId":1,"quantity":2}]
                                }
                                """.formatted(userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Sale created successfully with ID: 1"));
    }

    @Test
    void shouldAcceptInvalidCreatePayloadBecauseValidationIsMissing() throws Exception {
        UUID userId = UUID.randomUUID();
        CreateSaleCommand command = new CreateSaleCommand(userId, List.of());
        Sale sale = Sale.reconstitute(1L, userId, List.of(), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        when(mapper.toCommand(any())).thenReturn(command);
        when(createSaleService.save(command)).thenReturn(sale);

        mockMvc.perform(post("/api/v1/sales")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  \"userId\":null,
                                  \"Discount\":\"bad-discount\",
                                  \"items\":[]
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetSaleById() throws Exception {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(
                1L,
                userId,
                List.of(SaleDetail.reconstitute(1L, 1L, 10L, "Running Shoes", "Nike", "Sports", 2, Money.of(new BigDecimal("129.99")), BigDecimal.ZERO)),
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98")
        );
        SaleDtoResponse response = new SaleDtoResponse(
                1L,
                userId,
                List.of(new ItemsResponse("Running Shoes", "Nike", "Sports", 2, new BigDecimal("129.99"), BigDecimal.ZERO)),
                new BigDecimal("259.98"),
                BigDecimal.ZERO,
                new BigDecimal("259.98")
        );
        when(getSaleService.getById(1L)).thenReturn(sale);
        when(mapper.toResponse(sale)).thenReturn(response);

        mockMvc.perform(get("/api/v1/sales/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.items[0].name").value("Running Shoes"));
    }

    @Test
    void shouldGetSalesByDates() throws Exception {
        UUID userId = UUID.randomUUID();
        Sale sale = Sale.reconstitute(1L, userId, List.of(), new BigDecimal("259.98"), BigDecimal.ZERO, new BigDecimal("259.98"));
        SaleDtoResponse response = new SaleDtoResponse(1L, userId, List.of(), new BigDecimal("259.98"), BigDecimal.ZERO, new BigDecimal("259.98"));
        when(getSaleService.getByDates(any(LocalDateTime.class), any(LocalDateTime.class), any()))
                .thenReturn(new PageImpl<>(List.of(sale)));
        when(mapper.toResponse(sale)).thenReturn(response);

        mockMvc.perform(get("/api/v1/sales")
                        .param("startDate", "2026-07-01")
                        .param("endDate", "2026-07-03")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "date")
                        .param("sortDirection", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1));
    }
}
