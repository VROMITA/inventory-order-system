package com.vromita.inventory_order_system.dto;

public record WarehouseResponse(

        Long id,
        String identityCode,
        String name,
        String address
) { }
