package com.shop.service.impl;

import com.google.common.collect.Multimap;
import com.shop.entity.Product;
import com.shop.service.FilterService;
import com.shop.utils.products.Description;
import com.shop.utils.products.DescriptionCategory;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("filterService")
public class FilterServiceImpl implements FilterService {

    @Override
    public List<Product> productsByProducer(List<Product> productList, List<String> listProducer) {
        List<Product> list = new ArrayList<>();

        for (Product product : productList) {
            for (String producer : listProducer) {
                if (product.getProducer().equalsIgnoreCase(producer)) {
                    list.add(product);
                }
            }
        }

        return list;
    }

    @Override
    public List<Product> productsByFiltersDescription(List<Product> productList, Multimap<String, String> listFilterDescription) {
        List<Product> list = new ArrayList<>();

        for (Product product : productList){
            for (DescriptionCategory descCategory : product.getDescription()) {
                for (Description desc : descCategory.getDescriptionList()) {
                    for (Map.Entry<String, String> filter : listFilterDescription.entries()) {
                        if (desc.getDataDesc().equalsIgnoreCase(filter.getValue()) && desc.getNameDesc().equalsIgnoreCase(filter.getKey())) {
                            list.add(product);
                        }
                    }
                }
            }
        }

        return list;
    }
}