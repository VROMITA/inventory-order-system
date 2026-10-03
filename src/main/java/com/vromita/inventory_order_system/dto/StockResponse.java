package com.vromita.inventory_order_system.dto;

public record StockResponse(

        Long id,
        ProductResponse product,
        WarehouseResponse warehouse,
        int quantity

) {
}
