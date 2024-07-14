package com.bdny.product_management_api.service;

import com.bdny.product_management_api.dto.ProductDTO;
import com.bdny.product_management_api.exception.ProductIdViolationException;
import com.bdny.product_management_api.exception.ProductNotFoundException;
import com.bdny.product_management_api.mapper.ProductMapper;
import com.bdny.product_management_api.model.Product;
import com.bdny.product_management_api.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.INSERT_WITH_ID_VIOLATION;
import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.PRODUCT_NOT_FOUND;
import static com.bdny.product_management_api.validationmessages.ProductValidationMessages.UPDATE_ID_VIOLATION;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> productList = productRepository.findAll();
        return ProductMapper.INSTANCE.toDtoList(productList);
    }

    public ProductDTO getProductById(Long id) {
        Product product = findProductById(id);
        return transformProductToProductDTO(product);
    }

    public ProductDTO insertProduct(ProductDTO productDTO) {
        if (productDTO.getId() != null) {
            logger.error("Attempted to insert a product with an existing id: {}", productDTO.getId());
            throw new ProductIdViolationException(INSERT_WITH_ID_VIOLATION);
        }

        Product product = ProductMapper.INSTANCE.productDTOToProduct(productDTO);

        logger.info("Inserting new product: {}", product);
        Product savedProduct = productRepository.save(product);
        return transformProductToProductDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO updatedProductDTO) {
        if(!Objects.equals(id, updatedProductDTO.getId())){
            throw new ProductIdViolationException(UPDATE_ID_VIOLATION);
        }

        Product product = findProductById(id);
        updateAllProductFields(product, updatedProductDTO);
        logger.info("Updating product with id: {}", id);
        Product updatedProduct = productRepository.save(product);
        return transformProductToProductDTO(updatedProduct);
    }

    public ProductDTO updatePrice(Long id, BigDecimal newPrice) {
        Product product = findProductById(id);
        logger.info("updating product current price {} with new price {}", product.getPrice(), newPrice);
        product.setPrice(newPrice);
        Product savedProduct = productRepository.save(product);
        return transformProductToProductDTO(savedProduct);
    }

    public void delete(Long id) {
        logger.info("Deleting product with id: {}", id);
        findProductById(id);
        productRepository.deleteById(id);
        logger.info("Product with id: {} deleted", id);
    }

    private Product findProductById(Long id) {
        logger.debug("Finding product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error(PRODUCT_NOT_FOUND + id);
                    return new ProductNotFoundException(PRODUCT_NOT_FOUND + id);
                });
    }

    private void updateAllProductFields(Product fetchedProduct, ProductDTO updatedProduct) {
        fetchedProduct.setName(updatedProduct.getName());
        fetchedProduct.setPrice(updatedProduct.getPrice());
        fetchedProduct.setQuantity(updatedProduct.getQuantity());
        fetchedProduct.setBrand(updatedProduct.getBrand());
        logger.debug("Product fields updated for product: {}", fetchedProduct);
    }

    private ProductDTO transformProductToProductDTO(Product product) {
        return ProductMapper.INSTANCE.productToProductDTO(product);
    }
}
