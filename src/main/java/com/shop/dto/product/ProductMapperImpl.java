package com.shop.dto.product;

import com.shop.dto.category.CategoryDTO;
import com.shop.dto.product.util.CountRatingUtil;
import com.shop.entity.Category;
import com.shop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductMapperImpl {

    @Autowired
    private CountRatingUtil countRatingUtil;


    public List<ProductDTO> productsToProductsDTO(List<Product> productList) {
        if ( productList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( productList.size() );
        for ( Product product : productList ) {
            list.add( productToProductDTO( product ) );
        }

        return list;
    }


    public ProductDTO productToProductDTO(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setRatings( countRatingUtil.rating( product.getRating() ) );
        productDTO.setIdProduct( product.getIdProduct() );
        productDTO.setName( product.getName() );
        productDTO.setProducer( product.getProducer() );
        List<Description> list = product.getDescription();
        if ( list != null ) {
            productDTO.setDescription( new ArrayList<Description>( list ) );
        }
        else {
            productDTO.setDescription( null );
        }
        productDTO.setPrice( product.getPrice() );
        List<byte[]> list1 = product.getListImages();
        if ( list1 != null ) {
            productDTO.setListImages( new ArrayList<byte[]>( list1 ) );
        }
        else {
            productDTO.setListImages( null );
        }
        productDTO.setIsActive( product.getIsActive() );
        productDTO.setCode( product.getCode() );
        productDTO.setQuantity( product.getQuantity() );
        productDTO.setDiscount( product.getDiscount() );
        productDTO.setCategory( categoryToCategoryDTO( product.getCategory() ) );

        return productDTO;
    }


    public Product productDTOToProduct(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setIdProduct( productDTO.getIdProduct() );
        product.setName( productDTO.getName() );
        product.setProducer( productDTO.getProducer() );
        List<Description> list = productDTO.getDescription();
        if ( list != null ) {
            product.setDescription( new ArrayList<Description>( list ) );
        }
        else {
            product.setDescription( null );
        }
        product.setPrice( productDTO.getPrice() );
        List<byte[]> list1 = productDTO.getListImages();
        if ( list1 != null ) {
            product.setListImages( new ArrayList<byte[]>( list1 ) );
        }
        else {
            product.setListImages( null );
        }
        product.setIsActive( productDTO.getIsActive() );
        product.setCode( productDTO.getCode() );
        product.setQuantity( productDTO.getQuantity() );
        product.setDiscount( productDTO.getDiscount() );
        product.setCategory( categoryDTOToCategory( productDTO.getCategory() ) );

        return product;
    }


    public CategoryDTO categoryToCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setIdCategory( category.getIdCategory() );
        categoryDTO.setName( category.getName() );

        return categoryDTO;
    }


    public Category categoryDTOToCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category category = new Category();

        category.setIdCategory( categoryDTO.getIdCategory() );
        category.setName( categoryDTO.getName() );

        return category;
    }
}
