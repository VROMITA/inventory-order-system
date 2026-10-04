package com.vromita.inventory_order_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockRequest(

        @NotNull(message ="product id mandatory")
        Long productId,
        @NotBlank(message = "warehouse id mandatory")
        Long warehouseId,
        @Positive(message = "quantity must be positive")
        int quantity
) {
}
