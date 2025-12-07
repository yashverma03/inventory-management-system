package com.app.modules.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "DTO for updating an inventory item")
public class UpdateInventoryDto {

    @Schema(description = "Inventory item name", example = "Laptop", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 255)
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Schema(description = "Inventory item description", example = "High-performance laptop for development", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 1000)
    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Schema(description = "Quantity available", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @Schema(description = "Price per unit", example = "999.99", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @Schema(description = "Category of the inventory item", example = "Electronics", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 255)
    @Size(max = 255, message = "Category must not exceed 255 characters")
    private String category;
}
