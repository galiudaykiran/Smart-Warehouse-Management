package com.smart_warehouse_management.Inventory.exception;

public class InventoryProductNotFoundException extends RuntimeException {

    public InventoryProductNotFoundException(String message) {
        super(message);
    }

}