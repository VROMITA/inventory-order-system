package com.vromita.inventory_order_system.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(

        @NotNull(message = "product id mandatory")
        Long productId,

        @Positive(message = "quantity must be positive")
        int quantity
) { }
