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

@Tag(name = "Productos (Products)", description = "Operaciones CRUD para la gestión del catálogo de productos")
public interface ProductApiDocs {

    @Operation(summary = "Crear un nuevo producto", description = "Añade un nuevo producto al catálogo con la información proporcionada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    ResponseEntity<Void> save(
            @Parameter(description = "Datos requeridos para crear el producto") @RequestBody CreateProductRequest request);

    @Operation(summary = "Buscar producto por ID", description = "Devuelve los detalles de un producto específico dado su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralProductResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    ResponseEntity<GeneralProductResponse> findById(
            @Parameter(description = "ID del producto a buscar", example = "1") @PathVariable Long id);

    @Operation(summary = "Listar todos los productos", description = "Devuelve un listado paginado con todos los productos registrados en el sistema filtrando opcionalmente por categoría, marca, precio y estado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos devuelta exitosamente")
    })
    ResponseEntity<Page<GeneralProductResponse>> findAll(
            @Parameter(description = "Número de página (0-indexed)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamaño de la página", example = "10") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo por el cual ordenar", example = "id") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Dirección de ordenamiento (asc/desc)", example = "asc") @RequestParam(defaultValue = "asc") String sortDirection,
            @Parameter(description = "Filtro por nombre de categoría") @RequestParam(required = false) String category,
            @Parameter(description = "Filtro por nombre de marca") @RequestParam(required = false) String brand,
            @Parameter(description = "Filtro por precio mínimo", example = "10.0") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Filtro por precio máximo", example = "100.0") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Filtro por estado activo (true/false)") @RequestParam(required = false) Boolean active);

    @Operation(summary = "Actualizar un producto", description = "Sobrescribe la información de un producto existente mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GeneralProductResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    ResponseEntity<GeneralProductResponse> update(
            @Parameter(description = "ID del producto a actualizar", example = "1") @PathVariable Long id,
            @Parameter(description = "Datos para actualizar el producto") @RequestBody UpdateProductRequest request);

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del catálogo de manera permanente usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    ResponseEntity<Void> delete(
            @Parameter(description = "ID del producto a eliminar", example = "1") @PathVariable Long id);
}
