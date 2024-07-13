package com.bdny.product_management_api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductDTO {

    private Long id;

    @NotEmpty(message = "Product name cannot be empty")
    @Size(max = 128, message = "Product name cannot exceed 128 characters")
    private String name;

    @DecimalMin(value = "0.01", message = "Product price must be at least 0.01")
    private BigDecimal price;

    @Min(value = 0, message = "Product quantity cannot be negative")
    private int quantity;

    @NotEmpty(message = "Product brand cannot be empty")
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
