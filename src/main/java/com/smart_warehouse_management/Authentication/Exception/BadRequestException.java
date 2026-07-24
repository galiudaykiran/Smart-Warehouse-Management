package com.smart_warehouse_management.Authentication.Exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}