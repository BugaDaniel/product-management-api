package com.bdny.product_management_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.Objects;

import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.BRAND_NOT_EMPTY;
import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.MIN_PRICE;
import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.NAME_MAX_CHARS;
import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.NAME_NOT_EMPTY;
import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.QUANTITY_NOT_NEGATIVE;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", initialValue = 14, allocationSize = 3)
    private Long id;

    @NotEmpty(message = NAME_NOT_EMPTY)
    @Size(max = 128, message = NAME_MAX_CHARS)
    private String name;

    @Column(name = "price", columnDefinition = "NUMERIC(10, 2)") // SQL-based database specific column definition
    @DecimalMin(value = "0.01", message = MIN_PRICE)
    private BigDecimal price;

    @Min(value = 0, message = QUANTITY_NOT_NEGATIVE)
    private int quantity;

    @NotEmpty(message = BRAND_NOT_EMPTY)
    private String brand;

    public Product() {}

    public Product(String name, BigDecimal price, int quantity, String brand) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", brand='" + brand + '\'' +
                '}';
    }
}
