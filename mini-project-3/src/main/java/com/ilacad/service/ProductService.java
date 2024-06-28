package com.ilacad.service;

import com.ilacad.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long id);
}
