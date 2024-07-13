package com.bdny.product_management_api.mapper;

import com.bdny.product_management_api.dto.ProductDTO;
import com.bdny.product_management_api.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    Product productDTOToProduct(ProductDTO productDTO);

    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> toDtoList(List<Product> productList);
}
