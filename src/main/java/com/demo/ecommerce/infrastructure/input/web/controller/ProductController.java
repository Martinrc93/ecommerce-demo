package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.product.command.CreateProductCommand;
import com.demo.ecommerce.application.port.in.product.command.UpdateProductCommand;
import com.demo.ecommerce.application.port.in.product.usecase.CreateProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.DeleteProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.GetProductUseCase;
import com.demo.ecommerce.application.port.in.product.usecase.UpdateProductUseCase;
import com.demo.ecommerce.domain.model.product.Product;
import com.demo.ecommerce.infrastructure.config.ApiPaths;
import com.demo.ecommerce.infrastructure.input.web.controller.docs.ProductApiDocs;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.CreateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.UpdateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.response.GeneralProductResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.ProductDtoMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping(ApiPaths.PRODUCTS)
@AllArgsConstructor
public class ProductController implements ProductApiDocs {

    private final CreateProductUseCase createProductService;
    private final GetProductUseCase getProductService;
    private final UpdateProductUseCase updateProductService;
    private final DeleteProductUseCase deleteProductService;
    private final ProductDtoMapper productDtoMapper;

    @Override
    @PostMapping
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

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GeneralProductResponse> findById(@PathVariable Long id) {

        GeneralProductResponse response = productDtoMapper.toResponse(getProductService.getById(id));
        return ResponseEntity.ok(response);

    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<GeneralProductResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean active) {

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);

        Page<GeneralProductResponse> response = getProductService.getAll(category, brand, minPrice, maxPrice, active, pageable)
                .map(productDtoMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<GeneralProductResponse> update(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateProductRequest request){

        UpdateProductCommand command = productDtoMapper.toCommand(request);
        Product product = updateProductService.update(id,command);
        GeneralProductResponse response = productDtoMapper.toResponse(product);
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteProductService.execute(id);
        return ResponseEntity.noContent().build();
    }

}
