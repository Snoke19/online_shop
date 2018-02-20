package com.shop.dto.product;


import com.shop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class ProductMapperDecorator implements ProductMapper {


    private ProductMapper delegate;
    private CountRating countRating;


    @Autowired
    public void setDelegate(ProductMapper delegate) {
        this.delegate = delegate;
    }

    @Autowired
    public void setCountRating(CountRating countRating) {
        this.countRating = countRating;
    }

    @Override
    public ProductDTO productToProductDTO(Product product) {
        ProductDTO productDTO = delegate.productToProductDTO(product);
        productDTO.setRatings(countRating.getAverageRating(product.getRating()));
        return productDTO;
    }
}