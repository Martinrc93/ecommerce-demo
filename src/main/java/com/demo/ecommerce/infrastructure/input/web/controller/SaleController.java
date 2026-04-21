package com.demo.ecommerce.infrastructure.input.web.controller;


import com.demo.ecommerce.application.port.in.sale.usecase.CreateSaleUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/sales")
public class SaleController {

    private final CreateSaleUseCase createSaleService;

    @PostMapping()
    public ResponseEntity<String> create (){
        return null;
    }
}
