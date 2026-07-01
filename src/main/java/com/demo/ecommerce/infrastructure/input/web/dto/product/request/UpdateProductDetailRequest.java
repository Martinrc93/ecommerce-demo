package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data used to update product details")
public record UpdateProductDetailRequest(

        @Schema(description = "Product display name", example = "Running Shoes")
        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
        String name,

        @Schema(description = "Detailed product description", example = "Lightweight running shoes with a non-slip sole")
        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters long")
        String description,

        @Schema(description = "Product brand", example = "Nike")
        @NotBlank(message = "Brand is required")
        @Size(min= 3, max = 50, message = "Brand must be between 3 and 50 characters long")
        String brand,

        @Schema(description = "Primary product category", example = "Sports Footwear")
        @NotBlank(message = "Category is required")
        String category

) { }
