package com.ilacad.service;

import com.ilacad.entity.Cart;
import com.ilacad.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {

    List<Product> findAllProductsFromCart(String userEmail);

    void addToCart(String userEmail, Long productId, int numberOfItems);

    void removeProductFromCart(String userEmail, Long productId, int numberOfItems);

    void updateCredits(String userEmail, BigDecimal credits);

    void checkout(String userEmail, BigDecimal[] totalPrice);
}
