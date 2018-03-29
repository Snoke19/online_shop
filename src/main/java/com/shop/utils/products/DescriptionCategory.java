package com.shop.utils.products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescriptionCategory {

    private String nameCategoryDescription;
    private List<Description> descriptionList;
}
