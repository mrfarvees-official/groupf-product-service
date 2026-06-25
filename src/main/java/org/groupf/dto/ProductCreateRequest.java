package org.groupf.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductCreateRequest(
        @NotBlank(message = "Product name is required")
        String name,

        @NotNull(message = "Unit price is required")
        @Positive(message = "Unit price must be greater than 0")
        Double unitPrice,

        String description,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stock
) {
}
