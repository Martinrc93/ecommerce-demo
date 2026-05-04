package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.sale.usecase.CreateSaleUseCase;
import com.demo.ecommerce.application.port.in.sale.usecase.GetSaleUseCase;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.CreateSaleDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SaleDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.product.SaleDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Tag(name = "Sales", description = "Endpoints for managing sales ")
@AllArgsConstructor
@RestController
@RequestMapping("/sales")
public class SaleController {

    private final CreateSaleUseCase createSaleService;
    private final GetSaleUseCase getSaleService;
    private final SaleDtoMapper mapper;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateSaleDtoRequest dto) {
        Sale sale = createSaleService.save(mapper.toCommand(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Sale created successfully with ID: " + sale.getId());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SaleDtoResponse> getById(@PathVariable Long id) {
        Sale sale = getSaleService.getById(id);
        if (sale == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapper.toResponse(sale));
    }

    @GetMapping
    public ResponseEntity<Page<SaleDtoResponse>> getByDates(@RequestParam String startDate, @RequestParam String endDate,
                                                 @RequestParam(defaultValue = "0")  int page,
                                                 @RequestParam(defaultValue = "10")  int size){

        Pageable pageable = PageRequest.of(page, size);
        //GetSaleByDatesCommand command = new GetSaleByDatesCommand(startDate,endDate);
        LocalDateTime sDate = LocalDate.parse(startDate).atStartOfDay();
        LocalDateTime eDate = LocalDate.parse(endDate).atTime(LocalTime.MAX);

        Page<Sale> sales = getSaleService.getByDates(sDate,eDate,pageable);

        Page<SaleDtoResponse> response = sales.map(mapper::toResponse);
        return ResponseEntity.ok(response);

    }

}