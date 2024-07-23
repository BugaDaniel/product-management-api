package com.bdny.product_management_api.validationmessages;

public class ProductValidationMessages {
    public static final String PRODUCT_NOT_FOUND = "Product not found with id: ";
    public static final String INSERT_WITH_ID_VIOLATION = "Can't insert a new product if it already has an id";
    public static final String UPDATE_ID_VIOLATION = "Product id is different than the provided id";
    public static final String NAME_NOT_EMPTY = "Product name cannot be empty";
    public static final String NAME_MAX_CHARS = "Product name cannot exceed 128 characters";
    public static final String NAME_INVALID_CHARS = "Product name can have only letters and digits";
    public static final String MIN_PRICE = "Product price must be at least 0.01";
    public static final String QUANTITY_NOT_NEGATIVE = "Product quantity cannot be negative";
    public static final String BRAND_NOT_EMPTY = "Product brand cannot be empty";
    public static final String BRAND_INVALID_CHARS = "Product brand can have only letters and digits";

}
