package com.shop.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.shop.dao.ProductsDAO;
import com.shop.dto.product.ProductMapper;
import com.shop.dto.product.ProductDTO;
import com.shop.dto.product.ProductMapImpl;
import com.shop.entity.Product;
import com.shop.service.FilterService;
import com.shop.service.ProductsService;
import com.shop.utils.products.Description;
import com.shop.utils.products.DescriptionCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service("productsService")
public class ProductsServiceImpl implements ProductsService {

    private ProductsDAO productsDAO;
    private ProductsService productsService;
    private ProductMapImpl productMapper;
    private FilterService filterService;

    @Autowired
    public void setProductsDAO(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setProductMapper(ProductMapImpl productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setFilterService(FilterService filterService) {
        this.filterService = filterService;
    }


    @Override
    @Transactional
    public List<ProductDTO> getAll() {
        List<Product> productDTOList = productsDAO.getAll();
        List<Product> newResultList = new ArrayList<>();

        for (Product p : productDTOList) {
            if (p.getIsActive()){
                newResultList.add(p);
            }
        }

        Collections.shuffle(newResultList);

        return productMapper.productsToProductsDTO(newResultList);
    }


    @Override
    @Transactional
    public ProductDTO get(Long id) {
        return productMapper.productToProductDTO(productsDAO.get(id));
    }


    @Override
    @Transactional
    public void delete(Long id) {
        productsDAO.delete(id);
    }


    @Override
    @Transactional
    public void update(ProductDTO entityDTO) {
        productsDAO.update(productMapper.productDTOToProduct(entityDTO));
    }


    @Override
    @Transactional
    public void add(ProductDTO entityDTO) {
        productsDAO.add(productMapper.productDTOToProduct(entityDTO));
    }


    @Override
    @Transactional
    public List<ProductDTO> getAdminProducts() {
        List<Product> productList = productsDAO.getAdminProducts();
        Collections.reverse(productList);
        return ProductMapper.mapper.productsAddAdminDTO(productList);
    }


    @Override
    @Transactional
    public void updateNameProduct(String name, Long id) {
        productsDAO.updateNameProduct(name, id);
    }


    @Override
    @Transactional
    public void updateProducer(String producer, Long id) {
        productsDAO.updateProducer(producer, id);
    }


    @Override
    @Transactional
    public void updateDescription(List<DescriptionCategory> desc, Long id) {
        productsDAO.updateDescription(desc, id);
    }


    @Override
    @Transactional
    public void updatePrice(BigDecimal price, Long id) {
        productsDAO.updatePrice(price, id);
    }


    @Override
    @Transactional
    public void updateImage(List<byte[]> imageJson, Long id) {
        productsDAO.updateImage(imageJson, id);
    }


    @Override
    @Transactional
    public void updateActive(boolean active, Long id) {
        productsDAO.updateActive(active, id);
    }


    @Override
    @Transactional
    public void updateQuantity(Integer number, Long id) {
        productsDAO.updateQuantity(number, id);
    }


    @Override
    @Transactional
    public void updateCategory(Long idCategory, Long id) {
        productsDAO.updateCategory(idCategory, id);
    }


    @Override
    @Transactional
    public void updateCode(String code, Long id) {
        productsDAO.updateCode(code, id);
    }


    @Override
    @Transactional
    public void deleteOneImageProduct(int indexImage, Long idProduct) {
        ProductDTO productDTO = productsService.get(idProduct);
        productDTO.getListImages().remove(indexImage);
        productsService.updateImage(productDTO.getListImages(), productDTO.getIdProduct());
    }


    @Override
    @Transactional
    public void deleteProductSelected(List<Long> selected) {

        for (Long aSelected : selected) {
            productsDAO.delete(aSelected);
        }
    }


    @Override
    @Transactional
    public void setDiscount(List<Long> idList, Integer discount) {
        idList.forEach(id -> productsDAO.setDiscount(id, discount));
    }


    @Override
    @Transactional
    public List<ProductDTO> getProductsByRange(Integer start, String category) {
        return productMapper.productsToProductsDTO(productsDAO.getProductsByRange(start, category));
    }


    @Override
    @Transactional
    public List<ProductDTO> getAllProductsByCategory(String category) {
        return productMapper.productsToProductsDTO(productsDAO.getAllProductsByCategory(category));
    }


    @Override
    @Transactional
    public Map<String, Long> getAllProducerWithCountProducts() {
        Map<String, Long> results = new HashMap<>();
        List<Object[]> list = productsDAO.getAllProducerWithCountProducts();

        for (Object[] object : list) {
            results.put((String)object[0], (Long)object[1]);
        }

        return results;
    }


    @Override
    @Transactional
    public Multimap<String, Map<String, Integer>> getSideBarFilterProducts(String category, List<String> producers, Integer max, Integer min) {

        List<Product> descriptionCategories = new ArrayList<>();

        if (!producers.isEmpty()) {
            for (Product product : productsDAO.getAllProductsByCategory(category)) {
                for (String str : producers) {
                    if (str.equalsIgnoreCase(product.getProducer())) {
                        descriptionCategories.add(product);
                    }
                }
            }

        }else if (!producers.isEmpty() && max != 0 && min >= 0) {

            List<Product> productList = productsDAO.getAllProductsByCategory(category)
                    .stream()
                    .filter(product -> product.getPrice().intValue() >= min && product.getPrice().intValue() <= max)
                    .collect(Collectors.toList());

            for (Product product : productList) {
                for (String str : producers) {
                    if (str.equalsIgnoreCase(product.getProducer())) {
                        descriptionCategories.add(product);
                    }
                }
            }
        } else if(max != 0 && min >= 0) {

            List<Product> productList = productsDAO.getAllProductsByCategory(category)
                    .stream()
                    .filter(product -> product.getPrice().intValue() >= min && product.getPrice().intValue() <= max)
                    .collect(Collectors.toList());

            if (!productList.isEmpty()){
                descriptionCategories = productList;
            }else {
                descriptionCategories = productsDAO.getAllProductsByCategory(category);
            }
        } else {
            descriptionCategories = productsDAO.getAllProductsByCategory(category);
        }

        List<List<DescriptionCategory>> listOfListDescription = descriptionCategories.stream().map(Product::getDescription).collect(Collectors.toList());

        List<DescriptionCategory> listDescription = listOfListDescription.stream().flatMap(List::stream).collect(Collectors.toList());

        List<Description> descriptionList = listDescription.stream().flatMap(d -> d.getDescriptionList().stream()).collect(Collectors.toList());

        Map<Description, Integer> middleMap = new HashMap<>();

        for(Description s: descriptionList){
            middleMap.put(s,Collections.frequency(descriptionList,s));
        }

        Multimap<String, Map<String, Integer>> finalMap = ArrayListMultimap.create();

        for (Map.Entry<Description, Integer> data : middleMap.entrySet()) {
            finalMap.put(data.getKey().getNameDesc(), new HashMap<String, Integer>(){{put(data.getKey().getDataDesc(), data.getValue());}});
        }

        return finalMap;
    }


    @Override
    @Transactional
    public Map<String, Long> getAllProducerWithCountProductsByFilter(Multimap<String, String> filter, String category) {

        List<Product> list = productsDAO.getAllProductsByCategory(category);
        List<Product> listNew;

        if (!filter.isEmpty()){
            listNew = filterService.productsByFiltersDescription(list, filter);
        }else {
            return getAllProducerWithCountProducts();
        }

        return listNew.stream().distinct().collect(Collectors.groupingBy(Product::getProducer, Collectors.counting()));
    }


    @Override
    @Transactional
    public List<ProductDTO> getAllProductsByFilters(Multimap<String, String> filters, List<String> producers, String category, Integer max, Integer min) {

        List<Product> productList = productsDAO.getAllProductsByCategory(category);

        List<Product> productListNew = new ArrayList<>();


        if (!filters.isEmpty() && !producers.isEmpty()) {

            List<Product> listProductsByProducer = filterService.productsByProducer(productList, producers);

            productListNew.clear();
            productListNew.addAll(filterService.productsByFiltersDescription(listProductsByProducer, filters));

        } else if (!producers.isEmpty()){

            productListNew.addAll(filterService.productsByProducer(productList, producers));

        } else if (!filters.isEmpty()){

            productListNew.addAll(filterService.productsByFiltersDescription(productList, filters));

        } else {
            return productMapper.productsToProductsDTO(productsDAO.getAllProductsByCategory(category));
        }


        if (max != 0 && min >= 0 && !filters.isEmpty()) {

            productListNew = filterService.productsByFiltersDescriptionAndProducer(productList, filters)
                    .stream()
                    .filter(v -> v.getPrice().intValue() >= min && v.getPrice().intValue() <= max)
                    .collect(Collectors.toList());

        } else if (max != 0 && min >= 0 && !producers.isEmpty()) {

            productListNew = filterService.productsByFiltersDescriptionAndProducer(productList, producers)
                    .stream()
                    .filter(v -> v.getPrice().intValue() >= min && v.getPrice().intValue() <= max)
                    .collect(Collectors.toList());

        } else if (max != 0 && min >= 0 && !filters.isEmpty() && !producers.isEmpty()) {

            productListNew = filterService.productsByFiltersDescriptionAndProducer(productList, filters, producers)
                    .stream()
                    .filter(v -> v.getPrice().intValue() >= min && v.getPrice().intValue() <= max)
                    .collect(Collectors.toList());
        }

        return productMapper.productsToProductsDTO(productListNew);
    }


    @Override
    @Transactional
    public List<ProductDTO> getAllProductsByPrice(Multimap<String, String> filters,
                                                  List<String> producers,
                                                  String category, Integer max, Integer min) {

        List<Product> productList = productsDAO.getAllProductsByCategory(category);
        List<Product> productListNew = new ArrayList<>();

        for (Product product : productList){
            if (product.getPrice().intValue() >= min &&product.getPrice().intValue() <= max){
                productListNew.add(product);
            }
        }

        return productMapper.productsToProductsDTO(productListNew);
    }
}