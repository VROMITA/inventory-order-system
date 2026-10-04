package com.vromita.inventory_order_system.exceptionhandler;

import com.vromita.inventory_order_system.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateSerialException.class)
    public ResponseEntity<String> handleDuplicateSerial(DuplicateSerialException e){
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateStockException.class)
    public ResponseEntity<String> handleDuplicateStock(DuplicateStockException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(DuplicateIdentityCodeException.class)
    public ResponseEntity<String> handleDuplicateIdentityCode(DuplicateIdentityCodeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStock(InsufficientStockException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InsufficientReturnException.class)
    public ResponseEntity<String> handleInsufficientReturn(InsufficientReturnException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidOrderStatusTransitionException.class)
    public ResponseEntity<String> handleInvalidOrderStatus(InvalidOrderStatusTransitionException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidReturnStatusException.class)
    public ResponseEntity<String> handleInvalidReturnStatus(InvalidReturnStatusException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

    }
}
