package com.demo.ecommerce.infrastructure.input.web.controller;

import com.demo.ecommerce.application.port.in.category.usecase.CreateCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.DeleteCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.GetCategoryUseCase;
import com.demo.ecommerce.application.port.in.category.usecase.UpdateCategoryUseCase;
import com.demo.ecommerce.domain.model.product.Category;
import com.demo.ecommerce.infrastructure.input.web.dto.category.request.CategoryDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import com.demo.ecommerce.infrastructure.input.web.mapper.CategoryDtoMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
@Tag(name = "Categories", description = "API para la gestión de categorías")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetCategoryUseCase getCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final CategoryDtoMapper categoryDtoMapper;

    @PostMapping
    public ResponseEntity<CategoryDtoResponse> create (@RequestBody CategoryDtoRequest request){
        Category category = createCategoryUseCase.execute(categoryDtoMapper.toCommand(request));
        CategoryDtoResponse response = categoryDtoMapper.toResponse(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDtoResponse> findById(@PathVariable Long id){
        Category category = getCategoryUseCase.getById(id);
        CategoryDtoResponse response = categoryDtoMapper.toResponse(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<CategoryDtoResponse>> findAll(@PageableDefault(size = 10, page = 0)
                                                             Pageable pageable){

        Page<CategoryDtoResponse> response = getCategoryUseCase.getAll(pageable)
                .map(categoryDtoMapper::toResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDtoResponse> findByName(@PathVariable String name){
        CategoryDtoResponse response = categoryDtoMapper.toResponse(getCategoryUseCase.getByName(name));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

}
