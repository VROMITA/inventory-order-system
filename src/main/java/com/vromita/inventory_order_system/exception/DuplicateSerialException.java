package com.vromita.inventory_order_system.exception;

public class DuplicateSerialException extends RuntimeException{

    public DuplicateSerialException(String serial){

        super("Product with serial '" + serial + "' already exists");
    }
}
