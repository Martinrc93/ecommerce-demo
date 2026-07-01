package com.demo.ecommerce.infrastructure.input.web.controller.docs;

import com.demo.ecommerce.infrastructure.input.web.dto.brand.request.BrandDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.brand.response.BrandDtoResponse;
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

@Tag(name = "Brands", description = "API for brand management")
public interface BrandApiDocs {

    @Operation(summary = "Create a new brand", description = "Creates a new brand from the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<BrandDtoResponse> save(
            @Parameter(description = "Data required to create the brand") @RequestBody BrandDtoRequest request);

    @Operation(summary = "Get a brand by ID", description = "Returns the details of a specific brand.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    ResponseEntity<BrandDtoResponse> findById(
            @Parameter(description = "Brand ID to search for", example = "1") @PathVariable Long id);

    @Operation(summary = "Get a brand by name", description = "Returns the details of a specific brand by its unique name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class))),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    ResponseEntity<BrandDtoResponse> findByName(
            @Parameter(description = "Brand name to search for", example = "Nike") @PathVariable String name);

    @Operation(summary = "List all brands", description = "Returns a paginated list of brands.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand list returned successfully")
    })
    ResponseEntity<Page<BrandDtoResponse>> findAll(
            @Parameter(description = "Page number (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field used for sorting", example = "name") @RequestParam(defaultValue = "name") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc") @RequestParam(defaultValue = "asc") String sortDirection);

    @Operation(summary = "Delete a brand", description = "Deletes a brand using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "Brand ID to delete", example = "1") @PathVariable Long id);

    @Operation(summary = "Update a brand", description = "Updates an existing brand using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brand updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BrandDtoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content)
    })
    ResponseEntity<BrandDtoResponse> update(
            @Parameter(description = "Brand ID to update", example = "1") @PathVariable Long id,
            @Parameter(description = "Data used to update the brand") @RequestBody BrandDtoRequest request);
}
