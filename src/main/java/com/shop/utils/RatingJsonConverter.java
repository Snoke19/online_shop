package com.shop.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.utils.products.Rating;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class RatingJsonConverter implements AttributeConverter<List<Rating>, String> {

    @Override
    public String convertToDatabaseColumn(List<Rating> ratings) {
        return new Gson().toJson(ratings);
    }

    @Override
    public List<Rating> convertToEntityAttribute(String dbData) {
        return new Gson().fromJson(dbData, new TypeToken<List<Rating>>(){}.getType());
    }
}