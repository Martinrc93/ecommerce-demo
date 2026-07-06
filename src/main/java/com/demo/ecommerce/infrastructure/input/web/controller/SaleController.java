package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.sale.usecase.CreateSaleUseCase;
import com.demo.ecommerce.application.port.in.sale.usecase.GetSaleUseCase;
import com.demo.ecommerce.application.service.sale.SaleSeedService;
import com.demo.ecommerce.domain.model.sale.Sale;
import com.demo.ecommerce.infrastructure.config.ApiPaths;
import com.demo.ecommerce.infrastructure.input.web.controller.docs.SaleApiDocs;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.CreateSaleDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SaleDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SeedSalesResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.SaleDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@RestController
@RequestMapping(ApiPaths.SALES)
public class SaleController implements SaleApiDocs {

    private final CreateSaleUseCase createSaleService;
    private final GetSaleUseCase getSaleService;
    private final SaleSeedService saleSeedService;
    private final SaleDtoMapper mapper;

    @Override
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateSaleDtoRequest dto) {
        Sale sale = createSaleService.save(mapper.toCommand(dto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Sale created successfully with ID: " + sale.getId());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SaleDtoResponse> getById(@PathVariable Long id) {
        Sale sale = getSaleService.getById(id);
        return ResponseEntity.ok(mapper.toResponse(sale));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<SaleDtoResponse>> getByDates(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        LocalDateTime sDate;
        LocalDateTime eDate;

        if (startDate == null || endDate == null) {
            sDate = LocalDate.now().atStartOfDay();
            eDate = LocalDate.now().atTime(LocalTime.MAX);
        } else {
            sDate = LocalDate.parse(startDate).atStartOfDay();
            eDate = LocalDate.parse(endDate).atTime(LocalTime.MAX);
        }

        Page<Sale> sales = getSaleService.getByDates(sDate,eDate,pageable);

        Page<SaleDtoResponse> response = sales.map(mapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/seed")
    public ResponseEntity<SeedSalesResponse> seedSales(@RequestParam(defaultValue = "120") Integer amount) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleSeedService.seed(amount));
    }
}
