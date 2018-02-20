package com.shop.dao.impl;

import com.shop.dao.UserDAO;
import com.shop.entity.User;
import com.shop.entity.UserRole;
import com.shop.utils.HibernateSessionDAO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDAO")
public class UserDAOImpl extends HibernateSessionDAO implements UserDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAll() {
        return getSession()
                .createQuery("from User u")
                .list();
    }

    @Override
    public User get(Long id) {
        return getSession().get(User.class, id);
    }

    @Override
    public void add(User entity) {
        getSession().save(entity);
    }

    @Override
    public void delete(Long id) {
        getSession()
                .createQuery("delete from User u where u.idUser = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public void update(User entity) {
        getSession().update(entity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        List<UserRole> userRoleList = getSession().createQuery("from UserRole ur where ur.role = 'USER'").list();

        List<User> userList = new ArrayList<>();
        for (UserRole ur : userRoleList) {
            userList.add(ur.getUser());
        }

        return userList;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllAdmins() {
        List<UserRole> userRoleList = getSession().createQuery("from UserRole ur where ur.role = 'ADMIN'").list();

        List<User> adminList = new ArrayList<>();
        for (UserRole ur : userRoleList) {
            adminList.add(ur.getUser());
        }

        return adminList;
    }

    @Override
    public void updateEnabled(boolean data, Long id) {
        getSession()
                .createQuery("update User r set r.enabled = :data where r.idUser = :id")
                .setParameter("data", data)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User findByEmail(String email) {
        List<User> list = getSession()
                .createQuery("from User u where u.email = :email")
                .setParameter("email", email)
                .list();
        return (list.isEmpty() ? null : list.get(0));
    }
}