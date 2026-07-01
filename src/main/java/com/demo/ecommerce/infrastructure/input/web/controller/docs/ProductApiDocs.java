package com.demo.ecommerce.infrastructure.input.web.controller.docs;

import com.demo.ecommerce.infrastructure.input.web.dto.product.request.CreateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.request.UpdateProductRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.product.response.GeneralProductResponse;
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

import java.math.BigDecimal;

@Tag(name = "Products", description = "CRUD operations for product catalog management")
public interface ProductApiDocs {

    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog using the provided information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<Void> save(
            @Parameter(description = "Data required to create the product") @RequestBody CreateProductRequest request);

    @Operation(summary = "Find product by ID", description = "Returns the details of a specific product by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralProductResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    ResponseEntity<GeneralProductResponse> findById(
            @Parameter(description = "Product ID to search for", example = "1") @PathVariable Long id);

    @Operation(summary = "List all products", description = "Returns a paginated list of all registered products, optionally filtered by category, brand, price, and status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list returned successfully")
    })
    ResponseEntity<Page<GeneralProductResponse>> findAll(
            @Parameter(description = "Page number (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field used for sorting", example = "id") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc") @RequestParam(defaultValue = "asc") String sortDirection,
            @Parameter(description = "Filter by category name") @RequestParam(required = false) String category,
            @Parameter(description = "Filter by brand name") @RequestParam(required = false) String brand,
            @Parameter(description = "Minimum price filter", example = "10.0") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price filter", example = "100.0") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Filter by active status (true/false)") @RequestParam(required = false) Boolean active);

    @Operation(summary = "Update a product", description = "Overwrites an existing product using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralProductResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    ResponseEntity<GeneralProductResponse> update(
            @Parameter(description = "Product ID to update", example = "1") @PathVariable Long id,
            @Parameter(description = "Data used to update the product") @RequestBody UpdateProductRequest request);

    @Operation(summary = "Delete a product", description = "Permanently deletes a product from the catalog using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Product ID to delete", example = "1") @PathVariable Long id);
}
