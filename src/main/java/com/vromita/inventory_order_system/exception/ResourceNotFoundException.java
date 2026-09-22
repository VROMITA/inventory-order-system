package com.vromita.inventory_order_system.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, Object id) {
        super(entityName + " not found with id: " + id);
    }
}
