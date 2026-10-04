package com.vromita.inventory_order_system.dto;

import com.vromita.inventory_order_system.model.OrderItem;

import java.time.LocalDateTime;

public record ReturnResponse(

        Long id,
        OrderItemResponse orderItem,
        int quantity,
        String reason,
        LocalDateTime returnDate
) {
}
