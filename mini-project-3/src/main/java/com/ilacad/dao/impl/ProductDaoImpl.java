package com.ilacad.dao.impl;

import com.ilacad.dao.ProductDao;
import com.ilacad.entity.Product;
import com.ilacad.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {

    @Override
    public Product insertProduct(Product product) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(product);
        entityTransaction.commit();
        entityManager.close();
        return product;
    }

    @Override
    public List<Product> findAllProducts() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Product> findProductById(long productId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Product product = entityManager.find(Product.class, productId);
            return Optional.ofNullable(product);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());

            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
        return Optional.ofNullable(product);
    }

    @Override
    public Optional<Product> removeProduct(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, id);
        if (product != null) {
            entityManager.remove(product);
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
        return Optional.ofNullable(product);
    }

    @Override
    public boolean isProductFoundByName(String email) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();

        TypedQuery<Boolean> query = entityManager.createQuery(
                "SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email", Boolean.class);
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
}
