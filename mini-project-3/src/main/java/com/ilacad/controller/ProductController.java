package com.ilacad.controller;

import com.ilacad.dto.ProductDto;
import com.ilacad.service.ProductService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public ProductDto addProduct(ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    public void deleteProduct(Long id) {
        productService.deleteProduct(id);
    }
}
