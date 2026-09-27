package com.vromita.inventory_order_system.exception;

public class DuplicateIdentityCodeException extends RuntimeException {
    public DuplicateIdentityCodeException(String identityCode) {
        super("Warehouse with identity code " + identityCode + " already exists" );
    }
}
