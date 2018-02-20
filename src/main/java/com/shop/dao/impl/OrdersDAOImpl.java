package com.shop.dao.impl;

import com.shop.dao.OrdersDAO;
import com.shop.dto.orderItems.OrderItemsDTO;
import com.shop.entity.OrderItems;
import com.shop.entity.Orders;
import com.shop.utils.HibernateSessionDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ordersDAO")
public class OrdersDAOImpl extends HibernateSessionDAO implements OrdersDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Orders> getAll() {
        return getSession()
                .createQuery("from Orders")
                .list();
    }

    @Override
    public Orders get(Long id) {
        return getSession().get(Orders.class, id);
    }

    @Override
    public void add(Orders entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete from Orders c where c.idOrders = :id")
                .setParameter("id", id)
                .list();
    }

    @Override
    public void update(Orders entity) {
        getSession().update(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Orders> getNewAndProcessOrders() {
        return getSession()
                .createQuery("from Orders o where o.status = 'new' and o.status = 'in_process'")
                .list();
    }

    @Override
    public void updateStatusOrder(String status, Long id) {
        getSession()
                .createQuery("update Orders o set o.status = :stat where o.idOrders = :idOrder")
                .setParameter("stat", status)
                .setParameter("idOrder", id)
                .executeUpdate();
    }
}