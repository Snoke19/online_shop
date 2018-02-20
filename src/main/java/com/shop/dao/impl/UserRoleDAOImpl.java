package com.shop.dao.impl;

import com.shop.dao.UserRoleDAO;
import com.shop.entity.User;
import com.shop.entity.UserRole;
import com.shop.utils.HibernateSessionDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRoleDAO")
public class UserRoleDAOImpl extends HibernateSessionDAO implements UserRoleDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<UserRole> getAll() {
        return getSession()
                .createQuery("from UserRole")
                .list();
    }

    @Override
    public UserRole get(Long id) {
        return getSession().get(UserRole.class, id);
    }

    @Override
    public void add(UserRole entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete from UserRole u where u.idUserRole = :id")
                .setParameter("id", id)
                .list();
    }

    @Override
    public void update(UserRole entity) {
        getSession().update(entity);
    }
}