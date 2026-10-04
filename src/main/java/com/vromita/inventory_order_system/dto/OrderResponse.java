package com.vromita.inventory_order_system.dto;

import com.vromita.inventory_order_system.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        Long id,
        String customerCode,
        OrderStatus status,
        LocalDateTime orderDate,
        List<OrderItemResponse> items
) {
}
