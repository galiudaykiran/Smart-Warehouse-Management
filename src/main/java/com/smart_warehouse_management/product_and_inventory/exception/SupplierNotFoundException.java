package com.smart_warehouse_management.Product_And_Inventory.exception;

public class SupplierNotFoundException extends RuntimeException {

    public SupplierNotFoundException(String message) {
        super(message);
    }
}