package com.ilacad.dao.impl;

import com.ilacad.dao.CartDao;
import com.ilacad.entity.Cart;
import com.ilacad.entity.Product;
import com.ilacad.exception.CartNotFoundException;
import com.ilacad.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CartDaoImpl implements CartDao {

    @Override
    public Cart insertCart(Cart cart) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.persist(cart);
        entityTransaction.commit();
        entityManager.close();
        return cart;
    }

    @Override
    public List<Product> findAllProductsFromCart(Long cartId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        String nativeQuery = "SELECT p.* FROM products p " +
                "JOIN carts_products cp ON p.id = cp.product_id " +
                "WHERE cp.cart_id = :cartId";

        return entityManager.createNativeQuery(nativeQuery, Product.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public void addToCart(Cart cart, Product product, int numberOfItems) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.clear();
            for (int i = 0; i < numberOfItems; i++) {
                cart.getProducts().add(product);
            }
            entityManager.merge(cart);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeProductFromCart(Cart cart, Long productId, int numberOfItems) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Cart managedCart = entityManager.find(Cart.class, cart.getId());
            Product product = entityManager.find(Product.class, productId);
            entityManager.createNativeQuery("ALTER TABLE carts_products DISABLE TRIGGER ALL").executeUpdate();
            for (int i = 0; i < numberOfItems; i++) {
                managedCart.getProducts().remove(product);
            }

            entityManager.merge(managedCart);
            entityManager.getTransaction().commit();
            entityManager.createNativeQuery("ALTER TABLE carts_products ENABLE TRIGGER ALL").executeUpdate();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean existsById(Long id) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        TypedQuery<Long> query = entityManager.createQuery(
                        "SELECT COUNT(c) FROM Cart c WHERE c.id = :id", Long.class)
                .setParameter("id", id);

        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public void removeAllProductsFromCart(Long cartId) {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            entityManager.getTransaction().begin();

            Cart managedCart = entityManager.find(Cart.class, cartId);
            if (managedCart == null) {
                throw new CartNotFoundException(cartId);
            }

            managedCart.getProducts().clear();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Error removing products from cart: " + e.getMessage());
        } finally {
            entityManager.close();
        }
    }
}
