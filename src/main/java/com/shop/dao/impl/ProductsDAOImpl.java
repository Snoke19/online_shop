package com.shop.dao.impl;

import com.shop.dao.ProductsDAO;
import com.shop.utils.products.Description;
import com.shop.entity.Product;
import com.shop.utils.HibernateSessionDAO;
import com.shop.utils.products.DescriptionCategory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository("productsDAO")
public class ProductsDAOImpl extends HibernateSessionDAO implements ProductsDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAll() {
        return getSession().createQuery("from Product").list();
    }

    @Override
    public Product get(Long id) {
        return getSession().get(Product.class, id);
    }

    @Override
    public void add(Product entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete Product p where p.idProduct = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void update(Product entity) {
        getSession().update(entity);
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAdminProducts() {
        return getSession().createQuery("from Product p").list();
    }


    @Override
    public void updateNameProduct(String name, Long id) {
        getSession()
                .createQuery("update Product p set p.name = :name where p.idProduct = :id")
                .setParameter("name", name)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateProducer(String producer, Long id) {
        getSession()
                .createQuery("update Product p set p.producer = :producer where p.idProduct = :id")
                .setParameter("producer", producer)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateDescription(List<DescriptionCategory> desc, Long id) {
        getSession()
                .createQuery("update Product p set p.description = :desc where p.idProduct = :id")
                .setParameter("desc", desc)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updatePrice(BigDecimal price, Long id) {
        getSession()
                .createQuery("update Product p set p.price = :price where p.idProduct = :id")
                .setParameter("price", price)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateImage(List<byte[]> imageJson, Long id) {
        getSession()
                .createQuery("update Product p set p.listImages = :image where p.idProduct = :id")
                .setParameter("image", imageJson)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateActive(boolean active, Long id) {
        getSession()
                .createQuery("update Product p set p.isActive = :active where p.idProduct = :id")
                .setParameter("active", active)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateQuantity(Integer number, Long id) {
        getSession()
                .createQuery("update Product p set p.quantity = :number where p.idProduct = :id")
                .setParameter("number", number)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateCategory(Long idCategory, Long id) {
        getSession()
                .createQuery("update Product p set p.category.idCategory = :idCategory where p.idProduct = :id")
                .setParameter("idCategory", idCategory)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void updateCode(String code, Long id) {
        getSession()
                .createQuery("update Product p set p.code = :code where p.idProduct = :id")
                .setParameter("code", code)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    public void setDiscount(Long id, Integer discount) {
        getSession()
                .createQuery("update Product p set p.discount = :discount where p.idProduct = :id")
                .setParameter("discount", discount)
                .setParameter("id", id)
                .executeUpdate();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getProductsByRange(Integer start, String category) {
        return getSession()
                .createQuery("from Product p where p.category.name = :category and p.isActive = true")
                .setParameter("category", category)
                .setFirstResult(start)
                .setMaxResults(12)
                .list();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Product> getAllProductsByCategory(String category) {
        return getSession()
                .createQuery("from Product p where p.category.name = :category and p.isActive = true")
                .setParameter("category", category)
                .list();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getAllProducerWithCountProducts() {
        return getSession()
                .createQuery("select p.producer, COUNT(p.idProduct) from Product p where p.isActive = true group by p.producer")
                .list();
    }
}