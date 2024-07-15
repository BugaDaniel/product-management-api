package com.bdny.product_management_api.mocks.model;

import com.bdny.product_management_api.model.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Random;

public class ProductMockUtil {

    public static final long availableProductId = 1L;
    public static final Random randomId = new Random();

    public enum ProductIdOptions{
        NO_ID,
        AVAILABLE_ID,
        RANDOM_ID
    }

    public static Product createProductMock(String name, BigDecimal price, int quantity, String brand, ProductIdOptions productIdOptions){
        Product product = new Product(name, price, quantity, brand);

        setProductId(
                product,
                switch (productIdOptions) {
                    case AVAILABLE_ID -> availableProductId;
                    case RANDOM_ID -> randomId.nextLong();
                    case NO_ID -> null;
                }
        );
        return product;
    }

    // because Product does not have a setter for id, reflection is needed to set it for tests
    public static void setProductId(Product product, Long id) {
        try {
            Field field = Product.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(product, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
