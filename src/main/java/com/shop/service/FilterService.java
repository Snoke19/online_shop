package com.shop.service;

import com.google.common.collect.Multimap;
import com.shop.entity.Product;

import java.util.List;
import java.util.Map;

public interface FilterService {

    List<Product> productsByProducer(List<Product> productList, List<String> listProducer);

    List<Product> productsByFiltersDescription(List<Product> productList, Multimap<String, String> listFilterDescription);
}
