package com.vromita.inventory_order_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReturnRequest(


        @NotNull(message = "Order item id mandatory")
        Long orderItemId,
        @Positive(message = "Quantity must be positive")
        int quantity,
        @NotBlank(message = "Please insert a reason - mandatory")
        String reason

) {
}
