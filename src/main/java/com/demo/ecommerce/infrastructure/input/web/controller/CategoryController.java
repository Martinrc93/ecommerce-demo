package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.category.command.CreateCategoryCommand;
import com.demo.ecommerce.application.port.in.category.usecase.CreateCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.DeleteCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.GetCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.UpdateCategoryUseCase;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.config.ApiPaths;
import com.demo.ecommerce.infrastructure.input.web.controller.docs.CategoryApiDocs;
import com.demo.ecommerce.infrastructure.input.web.dto.category.request.CategoryDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.CategoryDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.CATEGORIES)
@AllArgsConstructor
public class CategoryController implements CategoryApiDocs {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final CategoryDtoMapper categoryDtoMapper;

    @Override
    @PostMapping
    public ResponseEntity<CategoryDtoResponse> create(@RequestBody CategoryDtoRequest request){
        Category category = createCategoryUseCase.execute(categoryDtoMapper.toCommand(request));
        CategoryDtoResponse response = categoryDtoMapper.toResponse(category);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDtoResponse> findById(@PathVariable Long id){
        Category category = getCategoryUseCase.getById(id);
        CategoryDtoResponse response = categoryDtoMapper.toResponse(category);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Page<CategoryDtoResponse>> findAll(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "name") String sortBy,
                                                             @RequestParam(defaultValue = "asc") String sortDirection){

        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page,size,sort);

        Page<CategoryDtoResponse> response = getCategoryUseCase.getAll(pageable)
                .map(categoryDtoMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/by-name/{name}")
    public ResponseEntity<CategoryDtoResponse> findByName(@PathVariable String name){
        CategoryDtoResponse response = categoryDtoMapper.toResponse(getCategoryUseCase.getByName(name));
        return ResponseEntity.ok(response);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDtoResponse> update(@RequestBody CategoryDtoRequest request,
                                                      @PathVariable Long id) {

        CreateCategoryCommand command = categoryDtoMapper.toCommand(request);
        Category category = updateCategoryUseCase.execute(id,command);
        CategoryDtoResponse response = categoryDtoMapper.toResponse(category);
        return ResponseEntity.ok(response);
    }

}
