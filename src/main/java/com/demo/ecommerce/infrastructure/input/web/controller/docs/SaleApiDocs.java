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

@Tag(name = "Ventas (Sales)", description = "Operaciones relacionadas con la creación y consulta de ventas")
public interface SaleApiDocs {

    @Operation(summary = "Crear una nueva venta", description = "Registra una venta en el sistema descontando el stock de los productos indicados y calculando totales y descuentos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada exitosamente",
                    content = @Content(mediaType = "text/plain", schema = @Schema(type = "string", example = "Sale created successfully with ID: 1"))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o stock insuficiente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto o usuario no encontrado", content = @Content)
    })
    ResponseEntity<String> create(
            @Parameter(description = "Datos de los productos y el usuario para registrar la venta") @RequestBody CreateSaleDtoRequest dto);

    @Operation(summary = "Obtener venta por ID", description = "Recupera los detalles completos de una venta específica mediante su identificador, incluyendo los productos comprados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SaleDtoResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    ResponseEntity<SaleDtoResponse> getById(
            @Parameter(description = "ID único de la venta", example = "1") @PathVariable Long id);

    @Operation(summary = "Buscar ventas por fechas", description = "Devuelve un listado paginado de ventas realizadas dentro de un rango de fechas. Si no se proporcionan fechas, devuelve las ventas del día actual.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de ventas recuperado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Formato de fecha inválido", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    ResponseEntity<Page<SaleDtoResponse>> getByDates(
            @Parameter(description = "Fecha de inicio para la búsqueda (Formato: YYYY-MM-DD)", example = "2023-10-01") @RequestParam(required = false) String startDate,
            @Parameter(description = "Fecha de fin para la búsqueda (Formato: YYYY-MM-DD)", example = "2023-10-31") @RequestParam(required = false) String endDate,
            @Parameter(description = "Número de página a recuperar (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Cantidad de registros por página", example = "10") @RequestParam(defaultValue = "10") int size);
}
