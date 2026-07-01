package com.demo.ecommerce.infrastructure.input.web.controller.docs;

import com.demo.ecommerce.infrastructure.input.web.dto.sale.request.CreateSaleDtoRequest;
import com.demo.ecommerce.infrastructure.input.web.dto.sale.response.SaleDtoResponse;
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

@Tag(name = "Sales", description = "Operations related to creating and querying sales")
public interface SaleApiDocs {

    @Operation(summary = "Create a new sale", description = "Registers a sale by discounting stock for the selected products and calculating totals and discounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale created successfully",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Sale created successfully with ID: 1"))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or insufficient stock", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product or user not found", content = @Content)
    })
    ResponseEntity<String> create(
            @Parameter(description = "Product and user data required to register the sale") @RequestBody CreateSaleDtoRequest dto);

    @Operation(summary = "Get sale by ID", description = "Retrieves the full details of a specific sale by ID, including purchased products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale found successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaleDtoResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sale not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<SaleDtoResponse> getById(
            @Parameter(description = "Unique sale ID", example = "1") @PathVariable Long id);

    @Operation(summary = "Find sales by date range", description = "Returns a paginated list of sales within a date range. If no dates are provided, it returns the current day sales.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sales list retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date format", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    ResponseEntity<Page<SaleDtoResponse>> getByDates(
            @Parameter(description = "Start date for the search (format: YYYY-MM-DD)", example = "2023-10-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "End date for the search (format: YYYY-MM-DD)", example = "2023-10-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "Page number to retrieve (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of records per page", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Field used for sorting", example = "date") @RequestParam(defaultValue = "date") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "desc") @RequestParam(defaultValue = "desc") String sortDirection);
}
