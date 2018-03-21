package com.shop.service.impl;

import com.shop.dao.ProductsDAO;
import com.shop.dto.product.Description;
import com.shop.dto.product.ProductDTO;
import com.shop.dto.product.ProductMapperImpl;
import com.shop.entity.Product;
import com.shop.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Service("productsService")
public class ProductsServiceImpl implements ProductsService {

    private ProductsDAO productsDAO;
    private ProductsService productsService;
    private ProductMapperImpl productMapper;

    @Autowired
    public void setProductsDAO(ProductsDAO productsDAO) {
        this.productsDAO = productsDAO;
    }

    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Autowired
    public void setProductMapper(ProductMapperImpl productMapper) {
        this.productMapper = productMapper;
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
        return productMapper.productsToProductsDTO(productList);
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
    public void updateDescription(List<Description> desc, Long id) {
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
    public void setDiscount(List<Long> idList, Integer discount) {

        idList.forEach(id -> productsDAO.setDiscount(id, discount));
    }


    @Override
    @Transactional
    public List<String> getAllProducer() {
        return productsDAO.getAllProducer();
    }


    @Override
    @Transactional
    public List<String> getAllProducerByCategory(String nameCategory) {
        return productsDAO.getAllProducerByCategory(nameCategory);
    }


    @Override
    @Transactional
    public List<ProductDTO> getAllProductsBySomething(String something) {
        return productMapper.productsToProductsDTO(productsDAO.getAllProductsBySomething(something));
    }


    @Override
    @Transactional
    public Map<String, Long> getAllProductsWithCountProducts() {
        Map<String, Long> results = new HashMap<>();
        List<Object[]> list = productsDAO.getAllProductsWithCountProducts();

        for (Object[] object : list) {
            results.put((String)object[0], (Long)object[1]);
        }

        return results;
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
    public Map<String, Long> getAllProducerWithCountProductsByCategory(String nameCategory) {
        Map<String, Long> results = new HashMap<>();
        List<Object[]> list = productsDAO.getAllProducerWithCountProductsByCategory(nameCategory);

        for (Object[] object : list) {
            results.put((String)object[0], (Long)object[1]);
        }

        return results;
    }


    @Override
    @Transactional
    public Map<String, Long> getAllProductsWithCountProductsByProducer(String nameProducer) {
        Map<String, Long> results = new HashMap<>();
        List<Object[]> list = productsDAO.getAllProductsWithCountProductsByProducer(nameProducer);

        for (Object[] object : list) {
            results.put((String)object[0], (Long)object[1]);
        }

        return results;
    }


    @Override
    @Transactional
    public void deleteProductSelected(List<Long> selected) {
        for (Long aSelected : selected) {
            productsDAO.delete(aSelected);
        }
    }
}