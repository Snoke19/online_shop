package com.shop.service.impl;

import com.google.common.collect.Multimap;
import com.shop.entity.Product;
import com.shop.service.FilterService;
import com.shop.utils.products.Description;
import com.shop.utils.products.DescriptionCategory;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
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


    @Override
    public List<Product> productsByFiltersDescriptionAndProducer(List<Product> productList, Multimap<String, String> listFilterDescription, List<String> listProducer) {
        List<Product> list = new ArrayList<>();

        list.addAll(productsByProducer(productList, listProducer));
        list.addAll(productsByFiltersDescription(productList, listFilterDescription));

        Collections.shuffle(list);

        return list;
    }


    @Override
    public List<Product> productsByFiltersDescriptionAndProducer(List<Product> productList, Multimap<String, String> listFilterDescription) {
        return productsByFiltersDescription(productList, listFilterDescription);
    }


    @Override
    public List<Product> productsByFiltersDescriptionAndProducer(List<Product> productList, List<String> listProducer) {
        return productsByProducer(productList, listProducer);
    }


    @Override
    public List<Product> productsByPrice(List<Product> productList, Integer max, Integer min) {
        return productList.stream()
                .filter(v -> v.getPrice().intValue() >= min && v.getPrice().intValue() <= max)
                .collect(Collectors.toList());
    }

}