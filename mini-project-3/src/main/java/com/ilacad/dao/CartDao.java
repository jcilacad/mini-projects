package com.ilacad.dao;

import com.ilacad.entity.Cart;
import com.ilacad.entity.Product;

import java.util.List;

public interface CartDao {

    Cart insertCart(Cart cart);

    List<Product> findAllProductsFromCart(Long cartId);

    void addToCart(Cart cart, Product product, int numberOfItems);

    void removeProductFromCart(Cart cart, Long productId, int numberOfItems);

    boolean existsById(Long id);
}
