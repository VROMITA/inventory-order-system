package com.vromita.inventory_order_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "serial mandatory")
        @Size(max = 10, message = "characters limit 10")
        String serial,

        @NotBlank(message = "name mandatory")
        String name,

        @NotNull(message = "price mandatory")
        @Positive(message = "price must be positive")
        BigDecimal price
) { }
