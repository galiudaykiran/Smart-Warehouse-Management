package com.smart_warehouse_management.orders.exception;

public class ResourceIsNotFoundException extends RuntimeException {

    public ResourceIsNotFoundException(String message) {
        super(message);
    }
}