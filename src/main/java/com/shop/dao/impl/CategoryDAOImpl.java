package com.shop.dao.impl;

import com.shop.dao.CategoryDAO;
import com.shop.entity.Category;
import com.shop.utils.HibernateSessionDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryDAO")
public class CategoryDAOImpl extends HibernateSessionDAO implements CategoryDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> getAll() {
        return getSession()
                .createQuery("from Category")
                .list();
    }

    @Override
    public Category get(Long id) {
        return getSession().get(Category.class, id);
    }

    @Override
    public void add(Category entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete from Category c where c.idCategory = :id")
                .setParameter("id", id)
                .list();
    }

    @Override
    public void update(Category entity) {
        getSession().update(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object[]> getAllCategoriesWithCountProducts() {
        return getSession()
                .createQuery("select c.name, COUNT(p.idProduct), p from Product p left join p.category c where p.isActive = true group by c.idCategory")
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Category getCategoryByName(String name) {
        List<Category> categoryList = getSession()
                .createQuery("from Category c where c.name = :name")
                .setParameter("name", name)
                .list();
        return (categoryList.isEmpty() ? null : categoryList.get(0));
    }
}