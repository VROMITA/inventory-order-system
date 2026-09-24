package com.vromita.inventory_order_system.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Long productId, Long warehouseId) {
        super("Insufficient stock for product " + productId + " in warehouse " + warehouseId);
    }

    public InsufficientStockException(Long productId){
        super("Insufficient stock for product " + productId + " across all the warehouse");
    }

}
