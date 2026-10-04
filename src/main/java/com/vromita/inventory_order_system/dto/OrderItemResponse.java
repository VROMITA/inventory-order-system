package com.vromita.inventory_order_system.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long id,
        ProductResponse product,
        WarehouseResponse warehouse,
        int quantity,
        BigDecimal priceAtOrder

) {
}
