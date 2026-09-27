package com.vromita.inventory_order_system.dto;

import jakarta.validation.constraints.NotBlank;

public record WarehouseRequest(

        @NotBlank
        String identityCode,
        @NotBlank
        String name,
        @NotBlank
        String address
) {
}
