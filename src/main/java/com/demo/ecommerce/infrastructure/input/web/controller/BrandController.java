package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.in.brand.usecase.CreateBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.DeleteBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.GetBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.UpdateBrandUseCase;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.request.BrandDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.BrandDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/brands")
@Tag(name = "Brands", description = "API for brand management")
public class BrandController {

    private final CreateBrandUseCase createBrandService;
    private final GetBrandUseCase getBrandService;
    private final UpdateBrandUseCase updateBrandService;
    private final DeleteBrandUseCase deleteBrandService;
    private final BrandDtoMapper brandDtoMapper;

    @Operation(summary = "Create a new brand", description = "Creates a new brand from the provided data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BrandDtoResponse> save(
            @Parameter(description = "Data required to create the brand") @RequestBody BrandDtoRequest request ){

        CreateBrandCommand command = new CreateBrandCommand(request.name());
        Brand brand = createBrandService.execute(command);
        return ResponseEntity.ok(brandDtoMapper.toResponse(brand));
    }
    
    @Operation(summary = "Get a brand by ID", description = "Returns the details of a specific brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<BrandDtoResponse> findById(
            @Parameter(description = "Brand ID to search for", example = "1") @PathVariable Long id){
        Brand brand = getBrandService.getById(id);
        return ResponseEntity.ok(brandDtoMapper.toResponse(brand));
    }

    @GetMapping("{name}")
    public ResponseEntity<BrandDtoResponse> findByName(@PathVariable String name){

        BrandDtoResponse response = brandDtoMapper.toResponse(getBrandService.getByName(name));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<BrandDtoResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){

        Pageable pageable = Pageable.ofSize(size).withPage(page);

        Page<BrandDtoResponse> response = getBrandService.getAll(pageable)
                .map(brandDtoMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteBrandService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<BrandDtoResponse> update(@PathVariable Long id,
                                                   @RequestBody BrandDtoRequest request) {

        CreateBrandCommand command = brandDtoMapper.toCommand(request);
        Brand brand = updateBrandService.execute(id,command);
        return ResponseEntity.ok(brandDtoMapper.toResponse(brand));
    }

}
