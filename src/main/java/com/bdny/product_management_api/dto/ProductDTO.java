package com.bdny.product_management_api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.*;

public class ProductDTO {

    private Long id;

    @NotEmpty(message = NAME_NOT_EMPTY)
    @Size(max = 128, message = NAME_MAX_CHARS)
    private String name;

    @NotNull
    @DecimalMin(value = "0.01", message = MIN_PRICE)
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = QUANTITY_NOT_NEGATIVE)
    private Integer quantity;

    @NotEmpty(message = BRAND_NOT_EMPTY)
    private String brand;

    public ProductDTO() {}

    public ProductDTO(String name, BigDecimal price, int quantity, String brand) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
