package com.shop.dto.product;

import com.shop.dto.category.CategoryDTO;

import com.shop.entity.Category;
import com.shop.entity.Product;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring")
@DecoratedWith(ProductMapperDecorator.class)
public interface ProductMapper {

    List<ProductDTO> productsToProductsDTO(List<Product> productList);
    ProductDTO productToProductDTO(Product product);
    Product productDTOToProduct(ProductDTO productDTO);

    @IterableMapping(qualifiedByName="productsAddAdminDTO")
    List<ProductDTO> productsAddAdminDTO(List<Product> productList);

    @Named("productsAddAdminDTO")
    @Mappings({
            @Mapping(target = "listImages", ignore = true),
            @Mapping(target = "description", ignore = true)
    })
    ProductDTO productsAddAdminDTO(Product product);


    CategoryDTO categoryToCategoryDTO(Category category);
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
}