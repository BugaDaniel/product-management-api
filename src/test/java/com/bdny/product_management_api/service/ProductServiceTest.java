package com.bdny.product_management_api.service;

import com.bdny.product_management_api.dto.ProductDTO;
import com.bdny.product_management_api.exception.ProductNotFoundException;
import com.bdny.product_management_api.mapper.ProductMapper;
import com.bdny.product_management_api.mocks.model.ProductMockUtil;
import com.bdny.product_management_api.mocks.repository.ProductRepositoryMockSetup;
import com.bdny.product_management_api.model.Product;
import com.bdny.product_management_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.PRODUCT_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testGetAllProducts() {
        ProductRepositoryMockSetup.mockFindAll(productRepository);

        List<ProductDTO> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    void testGetProductById() {
        ProductRepositoryMockSetup.mockFindById(productRepository);

        ProductDTO foundProduct = productService.getProductById(ProductMockUtil.availableProductId);

        assertNotNull(foundProduct);
        assertEquals(ProductMockUtil.availableProductId, foundProduct.getId());
    }

    @Test
    void testGetProductById_NotFound() {
        ProductRepositoryMockSetup.mockFindById(productRepository);

        long id = 999L;
        Exception exception = assertThrows(ProductNotFoundException.class, () -> productService.getProductById(id));

        String expectedMessage = PRODUCT_NOT_FOUND + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInsertProduct() {
        ProductRepositoryMockSetup.mockSave(productRepository);

        String name = "insertionName";

        Product product = ProductMockUtil.createProductMock(name, BigDecimal.valueOf(241.12), 2, "aBrand", ProductMockUtil.ProductIdOptions.NO_ID);
        ProductDTO savedProduct = productService.insertProduct(ProductMapper.INSTANCE.productToProductDTO(product));

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());
        assertEquals(name, savedProduct.getName());
    }

    @Test
    void testUpdateProduct() {
        ProductRepositoryMockSetup.mockSave(productRepository);
        ProductRepositoryMockSetup.mockFindById(productRepository);

        String name = "updName";
        BigDecimal price = BigDecimal.valueOf(77.42);
        int quantity = 33;
        String brand = "anotherOne";
        Product productWithUpdatedFields = ProductMockUtil.createProductMock(name, price, quantity, brand, ProductMockUtil.ProductIdOptions.AVAILABLE_ID) ;

        ProductDTO updatedProduct = productService.updateProduct(ProductMockUtil.availableProductId, ProductMapper.INSTANCE.productToProductDTO(productWithUpdatedFields));

        assertNotNull(updatedProduct);
        assertEquals(name, updatedProduct.getName());
        assertEquals(price, updatedProduct.getPrice());
        assertEquals(quantity, updatedProduct.getQuantity());
        assertEquals(brand, updatedProduct.getBrand());
    }

    @Test
    void testDeleteProductById() {
        ProductRepositoryMockSetup.mockFindById(productRepository);
        ProductRepositoryMockSetup.mockDelete(productRepository);

        productService.delete(ProductMockUtil.availableProductId);

        verify(productRepository, times(1)).deleteById(ProductMockUtil.availableProductId);
    }

    @Test
    void testDeleteProductById_NotFound() {
        ProductRepositoryMockSetup.mockFindById(productRepository);

        long id = 8899L;
        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.delete(id);
        });

        String expectedMessage = PRODUCT_NOT_FOUND + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(productRepository, times(0)).deleteById(id);
    }
}
