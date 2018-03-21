package com.shop.dto.product.util;

import com.shop.dto.product.CountRating;
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
    public Double rating( List<com.shop.dto.product.Rating> in ) {
        return countRating.getAverageRating(in);
    }
}
