package com.vromita.inventory_order_system.exception;

public class InsufficientReturnException extends RuntimeException {
    public InsufficientReturnException(Long orderItemId) {
        super("Return quantity exceeds available quantity for order item " + orderItemId);
    }
}
