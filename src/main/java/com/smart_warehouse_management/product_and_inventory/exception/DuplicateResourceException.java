package com.smart_warehouse_management.Product_And_Inventory.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}