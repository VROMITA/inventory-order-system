package com.vromita.inventory_order_system.exception;

import com.vromita.inventory_order_system.model.OrderStatus;

public class InvalidOrderStatusTransitionException extends RuntimeException {
    public InvalidOrderStatusTransitionException(OrderStatus orderStatus, OrderStatus newStatus) {
        super("Transition from " + orderStatus + " to " + newStatus + " not possible");
    }
}
