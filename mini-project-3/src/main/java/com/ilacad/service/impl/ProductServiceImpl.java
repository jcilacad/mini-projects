package com.ilacad.service.impl;

import com.ilacad.dao.ProductDao;
import com.ilacad.dto.ProductDto;
import com.ilacad.entity.Product;
import com.ilacad.exception.InvalidInputException;
import com.ilacad.exception.ProductAlreadyExistsException;
import com.ilacad.exception.ProductNotFoundException;
import com.ilacad.mapper.ProductMapper;
import com.ilacad.service.ProductService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    protected Validator validator = factory.getValidator();

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            violations.forEach(v -> {
                String fieldName = v.getPropertyPath().toString();
                String errorMessage = v.getMessage();
                errors.put(fieldName, errorMessage);
            });
            throw new InvalidInputException(errors);
        }

        if (productDao.isProductFoundByName(productDto.getName())) {
            throw new ProductAlreadyExistsException(productDto.getName());
        }

        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        Product savedProduct = productDao.insertProduct(product);
        return ProductMapper.INSTANCE.productToProductDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productDao.findAllProducts()
                .stream()
                .map(ProductMapper.INSTANCE::productToProductDto)
                .toList();
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(productDto);
        if (!violations.isEmpty()) {
            Map<String, String> errors = new HashMap<>();
            violations.forEach(v -> {
                String fieldName = v.getPropertyPath().toString();
                String errorMessage = v.getMessage();
                errors.put(fieldName, errorMessage);
            });
            throw new InvalidInputException(errors);
        }

        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        return productDao.updateProduct(id, product)
                .map(ProductMapper.INSTANCE::productToProductDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public void deleteProduct(Long id) {
        productDao.removeProduct(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
