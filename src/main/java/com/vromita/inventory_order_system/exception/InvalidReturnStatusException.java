package com.vromita.inventory_order_system.exception;

import com.vromita.inventory_order_system.model.OrderStatus;

public class InvalidReturnStatusException extends RuntimeException {
    public InvalidReturnStatusException(OrderStatus currentStatus) {
        super("Cannot create return: order is in status " + currentStatus + ", must be DELIVERED");
    }
}
