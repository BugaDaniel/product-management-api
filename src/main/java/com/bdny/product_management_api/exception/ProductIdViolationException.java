package com.bdny.product_management_api.exception;

public class ProductIdViolationException extends RuntimeException {

    public ProductIdViolationException(String message) {
        super(message);
    }
}
