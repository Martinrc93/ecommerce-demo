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
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/brands")
@Tag(name = "Brands", description = "API para la gestión de marcas")
public class BrandController {

    private final CreateBrandUseCase createBrandService;
    private final GetBrandUseCase getBrandService;
    private final UpdateBrandUseCase updateBrandService;
    private final DeleteBrandUseCase deleteBrandService;
    private final BrandDtoMapper brandDtoMapper;

    @Operation(summary = "Crear una nueva marca", description = "Crea una nueva marca a partir de los datos proporcionados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca creada exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @PostMapping
    public ResponseEntity<BrandDtoResponse> save(
            @Parameter(description = "Datos requeridos para crear la marca") @RequestBody BrandDtoRequest request ){

        CreateBrandCommand command = new CreateBrandCommand(request.name());
        Brand brand = createBrandService.execute(command);
        BrandDtoResponse response = new BrandDtoResponse(brand.id(),brand.name());
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Obtener una marca por su ID", description = "Retorna los detalles de una marca específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada", content = @Content)
    })
    @GetMapping("{id}")
    public ResponseEntity<BrandDtoResponse> findById(
            @Parameter(description = "ID de la marca a buscar", example = "1") @PathVariable Long id){
        Brand brand = getBrandService.getById(id);
        BrandDtoResponse response = new BrandDtoResponse(brand.id(),brand.name());
        return ResponseEntity.ok(response);
    }

    @GetMapping("{name}")
    public ResponseEntity<BrandDtoResponse> findByName(@PathVariable String name){

        BrandDtoResponse response = brandDtoMapper.toResponse(getBrandService.getByName(name));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<Page<BrandDtoResponse>> findAll(@PageableDefault(size = 10,page = 0)
                                                              Pageable pageable) {
        Page<BrandDtoResponse> response = getBrandService.getAll(pageable)
                .map(brandDtoMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    /*
    @PutMapping("{id}")
    public ResponseEntity<BrandDtoResponse> update(@PathVariable Long id,
                                                   @RequestBody BrandDtoRequest request) {

                return
    }
     */

}
