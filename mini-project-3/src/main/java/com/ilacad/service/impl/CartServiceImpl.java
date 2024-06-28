package com.ilacad.service.impl;

import com.ilacad.dao.CartDao;
import com.ilacad.dao.ProductDao;
import com.ilacad.dao.UserDao;
import com.ilacad.entity.Cart;
import com.ilacad.entity.Product;
import com.ilacad.entity.User;
import com.ilacad.exception.*;
import com.ilacad.service.CartService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    @Override
    public List<Product> findAllProductsFromCart(String userEmail) {
        User user = userDao.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        Long cartId = user.getCart().getId();
        boolean existsById = cartDao.existsById(cartId);
        if (!existsById) {
            throw new CartNotFoundException(cartId);
        }

        return cartDao.findAllProductsFromCart(cartId);
    }

    @Override
    public void addToCart(String userEmail, Long productId, int numberOfItems) {
        User user = userDao.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (numberOfItems > product.getQuantity()) {
            throw new ProductQuantityExceededException("The requested quantity exceeds the available stock for this product.");
        }

        Cart cart = user.getCart();
        cartDao.addToCart(cart, product, numberOfItems);
        product.setQuantity(product.getQuantity() - numberOfItems);
        productDao.updateProduct(productId, product);
    }

    @Override
    public void removeProductFromCart(String userEmail, Long productId, int numberOfItems) {
        User user = userDao.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        List<Product> products = cartDao.findAllProductsFromCart(user.getCart().getId());
        if (numberOfItems > products.size()) {
            throw new ProductQuantityExceededException("The requested quantity exceeds the available items in the cart.");
        }

        Cart cart = user.getCart();
        cartDao.removeProductFromCart(cart, productId, numberOfItems);
        product.setQuantity(product.getQuantity() + numberOfItems);
        productDao.updateProduct(productId, product);
    }

    @Override
    public void updateCredits(String userEmail, BigDecimal credits) {
        User user = userDao.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        BigDecimal newCredits = user.getCredits().add(credits);
        userDao.updateCredits(user.getId(), newCredits);
    }

    @Override
    public void checkout(String userEmail, BigDecimal[] totalPrice) {
        User user = userDao.findUserByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException(userEmail));

        BigDecimal credits = user.getCredits();
        if (totalPrice[0].compareTo(credits) == 1) {
            throw new InsufficientCreditsException(credits);
        }

        BigDecimal newCredits = user.getCredits().subtract(totalPrice[0]);
        userDao.updateCredits(user.getId(), newCredits);
        cartDao.removeAllProductsFromCart(user.getCart().getId());
    }
}
