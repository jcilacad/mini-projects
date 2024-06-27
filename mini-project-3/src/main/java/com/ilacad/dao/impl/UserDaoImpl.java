package com.ilacad.dao.impl;

import com.ilacad.dao.UserDao;
import com.ilacad.entity.User;
import com.ilacad.util.Hashing;
import com.ilacad.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public User insertUser(User user) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            user.setPassword(Hashing.hash(user.getPassword()));
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user;
        } catch (Exception e) {
            entityTransaction.rollback();
            throw new RuntimeException("Error saving user: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean isUserFoundByEmail(String email) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT COUNT(s) > 0 FROM Student s WHERE s.email = :email", Boolean.class);
        query.setParameter("email", email);
        query.setMaxResults(1);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return false;
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);
        query.setMaxResults(1);

        try {
            User user = query.getSingleResult();
            return Optional.ofNullable(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }
}
