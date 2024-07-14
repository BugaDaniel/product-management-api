package com.bdny.product_management_api.validationmessages;

public class ProductValidationMessages {
    public static final String PRODUCT_NOT_FOUND = "Product not found with id: ";
    public static final String INSERT_WITH_ID_VIOLATION = "Can't insert a new product if it already has an id";
    public static final String UPDATE_ID_VIOLATION = "Product id is different than the provided id";
}
