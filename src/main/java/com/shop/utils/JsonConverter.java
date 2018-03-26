package com.shop.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shop.dto.product.Description;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.List;
import java.util.Map;

@Converter
public class JsonConverter implements AttributeConverter<List<Map<String, List<Description>>>, String> {

    @Override
    public String convertToDatabaseColumn(List<Map<String, List<Description>>> attribute) {
        return new Gson().toJson(attribute);
    }

    @Override
    public List<Map<String, List<Description>>> convertToEntityAttribute(String dbData) {
        return new Gson().fromJson(dbData, new TypeToken<List<Map<String, List<Description>>>>() {}.getType());
    }
}