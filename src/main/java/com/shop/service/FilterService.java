package com.shop.service;

import com.shop.entity.Product;

import java.util.List;

public interface FilterService {

    List<Product> productsByProducer(List<Product> productList, List<String> listProducer);

    List<Product> productsByFiltersDescription(List<Product> productList, List<String> listFilterDescription);
}
