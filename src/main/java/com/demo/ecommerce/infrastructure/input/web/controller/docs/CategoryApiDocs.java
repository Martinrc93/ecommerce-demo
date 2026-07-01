package com.demo.ecommerce.infrastructure.input.web.controller.docs;

import com.demo.ecommerce.infrastructure.input.web.dto.category.request.CategoryDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.category.response.CategoryDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Categories", description = "API for category management")
public interface CategoryApiDocs {

    @Operation(summary = "Create a new category", description = "Creates a new category from the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<CategoryDtoResponse> create(
            @Parameter(description = "Data required to create the category") @RequestBody CategoryDtoRequest request);

    @Operation(summary = "Get category by ID", description = "Returns the details of a specific category.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDtoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    ResponseEntity<CategoryDtoResponse> findById(
            @Parameter(description = "Category ID to search for", example = "1") @PathVariable Long id);

    @Operation(summary = "List all categories", description = "Returns a paginated list of categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category list returned successfully")
    })
    ResponseEntity<Page<CategoryDtoResponse>> findAll(
            @Parameter(description = "Page number (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field used for sorting", example = "name") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc") @RequestParam(defaultValue = "asc") String sortDirection);

    @Operation(summary = "Get category by name", description = "Returns a category by its unique name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDtoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    ResponseEntity<CategoryDtoResponse> findByName(
            @Parameter(description = "Category name to search for", example = "Electronics") @PathVariable String name);

    @Operation(summary = "Delete a category", description = "Deletes a category using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Category ID to delete", example = "1") @PathVariable Long id);

    @Operation(summary = "Update a category", description = "Updates an existing category using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found", content = @Content)
    })
    ResponseEntity<CategoryDtoResponse> update(
            @Parameter(description = "Data used to update the category") @RequestBody CategoryDtoRequest request,
            @Parameter(description = "Category ID to update", example = "1") @PathVariable Long id);
}
