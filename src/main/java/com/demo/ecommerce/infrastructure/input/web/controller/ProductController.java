package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.application.port.in.product.usecase.CreateProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.DeleteProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.GetProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.UpdateProductUseCase;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.CreateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.UpdateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.response.GeneralProductResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.product.ProductDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Products", description = "Endpoints for managing products")
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductService;
    private final GetProductUseCase getProductService;
    private final UpdateProductUseCase updateProductService;
    private final DeleteProductUseCase deleteProductService;
    private final ProductDtoMapper productDtoMapper;

    @PostMapping
    @Operation(summary = "Create a new product",description = "Create a new product with the provided data")
    public ResponseEntity<Void> save(@RequestBody @Valid CreateProductRequest request) {

        CreateProductCommand command = productDtoMapper.toCommand(request);
        Product product = createProductService.execute(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralProductResponse> findById(@PathVariable Long id) {

        GeneralProductResponse response = productDtoMapper.toResponse(getProductService.getById(id));
        return ResponseEntity.ok(response);

    }

    @GetMapping("/all")
    public ResponseEntity<Page<GeneralProductResponse>> findAll(@Parameter(hidden = true) @PageableDefault Pageable pageable) {

        Page<GeneralProductResponse> response = getProductService.getAll(pageable)
                .map(productDtoMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<GeneralProductResponse>> findByCategory(@RequestParam String category,
                                                                       @Parameter(hidden = true) @PageableDefault(page = 0,size = 10) Pageable pageable) {

        Page<GeneralProductResponse> response = getProductService.getByCategory(category,pageable)
                .map(productDtoMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralProductResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateProductRequest request){

        UpdateProductCommand command = productDtoMapper.toCommand(request);
        Product product = updateProductService.update(id,command);
        GeneralProductResponse response = productDtoMapper.toResponse(product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteProductService.execute(id);
        return ResponseEntity.noContent().build();
    }

}