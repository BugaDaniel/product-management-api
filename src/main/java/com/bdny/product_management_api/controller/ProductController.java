package com.bdny.product_management_api.controller;

import com.bdny.product_management_api.dto.ProductPriceUpdateRequest;
import com.bdny.product_management_api.dto.ProductDTO;
import com.bdny.product_management_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> getAllProducts() {
        List<EntityModel<ProductDTO>> products = productService.getAllProducts().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(toEntityModel(product));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductDTO>> insertProduct(@Valid @RequestBody ProductDTO product) {
        ProductDTO savedProduct = productService.insertProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEntityModel(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductDTO>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO product) {
        ProductDTO updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(toEntityModel(updatedProduct));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<EntityModel<ProductDTO>> changePrice(@PathVariable Long id, @Valid @RequestBody ProductPriceUpdateRequest productPriceUpdateRequest) {
        ProductDTO updatedProduct = productService.updatePrice(id, productPriceUpdateRequest.getNewPrice());
        return ResponseEntity.ok(toEntityModel(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok("Product with ID " + id + " deleted successfully.");
    }

    private EntityModel<ProductDTO> toEntityModel(ProductDTO product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")
        );
    }
}
