package com.shop.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.utils.products.DescriptionCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;

@Converter
public class JsonConverter implements AttributeConverter<List<DescriptionCategory>, String> {

    @Override
    public String convertToDatabaseColumn(List<DescriptionCategory> attribute) {
        return new Gson().toJson(attribute);
    }

    @Override
    public List<DescriptionCategory> convertToEntityAttribute(String dbData) {
        return new Gson().fromJson(dbData, new TypeToken<List<DescriptionCategory>>() {}.getType());
    }
}