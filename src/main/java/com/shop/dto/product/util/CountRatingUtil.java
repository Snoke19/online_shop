package com.shop.dto.product.util;

import com.shop.utils.products.CountRating;
import com.shop.utils.products.Rating;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Named("TitleTranslator")
@Component
public class CountRatingUtil {

    @Autowired
    private CountRating countRating;

    @Named("RatingProducts")
    public Double rating( List<Rating> in ) {
        return countRating.getAverageRating(in);
    }
}
