package com.bdny.product_management_api.dto;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.MIN_PRICE;

public class ProductPriceUpdateRequest {

    @DecimalMin(value = "0.01", message = MIN_PRICE)
    private BigDecimal newPrice;

    // Getter and Setter
    public BigDecimal getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(BigDecimal newPrice) {
        this.newPrice = newPrice;
    }
}
