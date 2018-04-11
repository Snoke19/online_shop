package com.shop.service.impl;

import com.shop.entity.Product;
import com.shop.service.FilterService;
import com.shop.utils.products.Description;
import com.shop.utils.products.DescriptionCategory;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<Product> productsByFiltersDescription(List<Product> productList, List<String> listFilterDescription) {
        List<Product> list = new ArrayList<>();

        for (Product product : productList) {
            for (DescriptionCategory descCategory : product.getDescription()) {
                for (Description desc : descCategory.getDescriptionList()) {
                    for (String filter : listFilterDescription) {
                        if (desc.getDataDesc().equalsIgnoreCase(filter)) {
                            list.add(product);
                        }
                    }
                }
            }
        }

        return list;
    }
}
