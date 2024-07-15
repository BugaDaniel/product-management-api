package com.bdny.product_management_api.mocks.repository;

import com.bdny.product_management_api.mocks.model.ProductMockUtil;
import com.bdny.product_management_api.model.Product;
import com.bdny.product_management_api.repository.ProductRepository;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryMockSetup {

    public static void mockFindById(ProductRepository productRepository) {
        Mockito.when(productRepository.findById(Mockito.anyLong()))
                .thenAnswer(invocation -> {
                    Long id = invocation.getArgument(0);
                    // when findById is called, only for id = 1 will return a product
                    if (id.equals(ProductMockUtil.availableProductId)) {
                        Product product = ProductMockUtil.createProductMock("1Name", BigDecimal.valueOf(241.12), 2, "someBrand", ProductMockUtil.ProductIdOptions.AVAILABLE_ID);
                        return Optional.of(product);
                    } else {
                        return Optional.empty();
                    }
                });
    }

    public static void mockFindAll(ProductRepository productRepository) {
        Mockito.when(productRepository.findAll())
                .thenReturn(List.of(
                        ProductMockUtil.createProductMock("1Name1", BigDecimal.valueOf(242221.12), 7, "someBrand", ProductMockUtil.ProductIdOptions.RANDOM_ID),
                        ProductMockUtil.createProductMock("2Name2", BigDecimal.valueOf(1123.77), 77, "someBrand", ProductMockUtil.ProductIdOptions.RANDOM_ID)
                ));
    }

    public static void mockSave(ProductRepository productRepository) {
        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    Product product = invocation.getArgument(0);
                    // mock behavior for save in db
                    // if id == null means it tries to insert, so it returns a product with id after insert success
                    if (product.getId() == null){
                        ProductMockUtil.setProductId(product, ProductMockUtil.randomId.nextLong());
                    }
                    // if product already has id it means update is wanted
                    return product;
                });
    }

    public static void mockDelete(ProductRepository productRepository) {
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());
    }
}
