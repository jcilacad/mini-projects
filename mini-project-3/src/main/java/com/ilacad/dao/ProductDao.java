package com.ilacad.dao;

import com.ilacad.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Product insertProduct(Product product);

    List<Product> findAllProducts();

    Optional<Product> updateProduct(Long id, Product updatedProduct);

    Optional<Product> removeProduct(Long id);

    boolean isProductFoundByName(String name);

    Optional<Product> findProductById(long productId);
}
