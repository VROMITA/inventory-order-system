package com.vromita.inventory_order_system.exception;

public class DuplicateStockException extends RuntimeException {
    public DuplicateStockException(Long productId, Long warehouseId) {
        super("There is already a stock with this product id " + productId + " and warehouse id " + warehouseId);
    }
}
