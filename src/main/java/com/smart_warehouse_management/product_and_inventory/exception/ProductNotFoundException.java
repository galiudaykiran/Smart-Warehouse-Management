package com.smart_warehouse_management.Product_And_Inventory.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}