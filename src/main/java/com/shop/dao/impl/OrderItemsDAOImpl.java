package com.shop.dao.impl;

import com.shop.dao.OrderItemsDAO;
import com.shop.entity.Category;
import com.shop.entity.OrderItems;
import com.shop.entity.User;
import com.shop.utils.HibernateSessionDAO;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("orderItemsDAO")
public class OrderItemsDAOImpl extends HibernateSessionDAO implements OrderItemsDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderItems> getAll() {
        return getSession()
                .createQuery("from OrderItems")
                .list();
    }

    @Override
    public OrderItems get(Long id) {
        return getSession().get(OrderItems.class, id);
    }

    @Override
    public void add(OrderItems entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete from OrderItems c where c.idOrderItems = :id")
                .setParameter("id", id)
                .list();
    }

    @Override
    public void update(OrderItems entity) {
        getSession().update(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderItems> getOrdersHistoryByIdUser(Long id) {
        return getSession()
                .createQuery("from OrderItems ot where ot.orders.user.idUser = :id")
                .setParameter("id", id)
                .list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<OrderItems> getOrderItemsByIdUser(Long id) {
        return getSession()
                .createQuery("from OrderItems ot where ot.orders.idOrders = :id")
                .setParameter("id", id).list();
    }
}