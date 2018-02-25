package com.shop.dto.product;

import com.shop.entity.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;


@Component
public abstract class ProductMapperDecorator implements ProductMapper {

    @Qualifier("delegate")
    private ProductMapper delegate;
    private CountRating countRating;


    @Autowired
    public void setCountRating(CountRating countRating) {
        this.countRating = countRating;
    }


    @Autowired
    public void setDelegate(ProductMapper delegate) {
        this.delegate = delegate;
    }


    @Override
    public ProductDTO productAddAdminDTO(Product product) {
        ProductDTO productDTO = delegate.productAddAdminDTO(product);
        productDTO.setRatings(countRating.getAverageRating(product.getRating()));
        return productDTO;
    }

    @Override
    public ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = delegate.productToProductDTO(product);
        productDTO.setRatings(countRating.getAverageRating(product.getRating()));
        return productDTO;
    }
}