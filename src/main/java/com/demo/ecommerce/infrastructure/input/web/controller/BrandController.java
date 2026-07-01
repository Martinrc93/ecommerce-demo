package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.brand.command.CreateBrandCommand;
import com.demo.ecommerce.application.port.in.brand.usecase.CreateBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.DeleteBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.GetBrandUseCase;
import com.demo.ecommerce.application.port.in.brand.usecase.UpdateBrandUseCase;
import com.demo.ecommerce.domain.model.product.Brand;
import com.demo.ecommerce.infrastructure.config.ApiPaths;
import com.demo.ecommerce.infrastructure.input.web.controller.docs.BrandApiDocs;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.request.BrandDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.BrandDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(ApiPaths.BRANDS)
public class BrandController implements BrandApiDocs {

    private final CreateBrandUseCase createBrandService;
    private final GetBrandUseCase getBrandService;
    private final UpdateBrandUseCase updateBrandService;
    private final DeleteBrandUseCase deleteBrandService;
    private final BrandDtoMapper brandDtoMapper;

    @Override
    @PostMapping
    public ResponseEntity<BrandDtoResponse> save(@RequestBody BrandDtoRequest request ){

        CreateBrandCommand command = new CreateBrandCommand(request.name());
        Brand brand = createBrandService.execute(command);
        return ResponseEntity.ok(brandDtoMapper.toResponse(brand));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BrandDtoResponse> findById(@PathVariable Long id){
        Brand brand = getBrandService.getById(id);
        return ResponseEntity.ok(brandDtoMapper.toResponse(brand));
    }

    @Override
    @GetMapping("/{name}")
    public ResponseEntity<BrandDtoResponse> findByName(@PathVariable String name){

        BrandDtoResponse response = brandDtoMapper.toResponse(getBrandService.getByName(name));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<BrandDtoResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "name") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortDirection){

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BrandDtoResponse> response = getBrandService.getAll(pageable)
                .map(brandDtoMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteBrandService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<BrandDtoResponse> update(@PathVariable Long id,
                                                   @RequestBody BrandDtoRequest request) {

        CreateBrandCommand command = brandDtoMapper.toCommand(request);
        Brand brand = updateBrandService.execute(id,command);
        return ResponseEntity.ok(brandDtoMapper.toResponse(brand));
    }

}
