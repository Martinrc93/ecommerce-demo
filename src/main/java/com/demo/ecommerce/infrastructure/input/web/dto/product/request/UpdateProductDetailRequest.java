package com.demo.ecommerce.infrastructure.input.web.dto.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateProductDetailRequest(

        @NotBlank(message = "Name is required")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters long")
        String name,

        @NotBlank(message = "Description is required")
        @Size(min = 10, max = 255, message = "Description must be between 10 and 255 characters long")
        String description,

        @NotBlank(message = "Brand is required")
        @Size(min= 3, max = 50, message = "Brand must be between 3 and 50 characters long")
        String brand,

        @NotBlank(message = "Category is required")
        String category

) { }