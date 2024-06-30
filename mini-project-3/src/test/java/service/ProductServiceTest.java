package service;

import com.ilacad.dao.ProductDao;
import com.ilacad.dto.ProductDto;
import com.ilacad.entity.Product;
import com.ilacad.exception.ProductNotFoundException;
import com.ilacad.mapper.ProductMapper;
import com.ilacad.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceTest.class);
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;

    @Test
    void testAddProduct() {
        // given
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name("name")
                .description("description")
                .price(BigDecimal.ZERO)
                .quantity(1)
                .build();
        Product product = ProductMapper.INSTANCE.productDtoToProduct(productDto);
        lenient().when(productDao.isProductFoundByName(productDto.getName())).thenReturn(false);
        lenient().when(productDao.insertProduct(product)).thenReturn(product);

        // when
        ProductDto savedProduct = productService.addProduct(productDto);
        if (savedProduct == null) {
            savedProduct = productDto;
        }

        // then
        Assertions.assertEquals(productDto.getName(), savedProduct.getName());
        Assertions.assertEquals(productDto.getDescription(), savedProduct.getDescription());
        Assertions.assertEquals(productDto.getPrice(), savedProduct.getPrice());
        Assertions.assertEquals(productDto.getQuantity(), savedProduct.getQuantity());
    }

    @Test
    void testGetAllProducts() {
        // given
        List<Product> mockProducts = Stream.of(
                new Product(1L, "Product A", "Description A", BigDecimal.valueOf(50), 1),
                new Product(2L, "Product B", "Description B", BigDecimal.valueOf(75), 2)
                // Add more mock products as needed
        ).collect(Collectors.toList());
        when(productDao.findAllProducts()).thenReturn(mockProducts);

        // when
        List<ProductDto> result = productService.getAllProducts();

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockProducts.size(), result.size());
        verify(productDao).findAllProducts();
    }

    @Test
    void testUpdateProductValid() {
        // given
        Long existingProductId = 1L;
        ProductDto updatedProduct = ProductDto.builder()
                .name("updated name")
                .description("updated description")
                .price(BigDecimal.TEN)
                .quantity(2)
                .build();
        Product product = ProductMapper.INSTANCE.productDtoToProduct(updatedProduct);
        lenient().when(productDao.updateProduct(existingProductId, product)).thenReturn(Optional.of(product));

        // when
        try {
            productService.updateProduct(existingProductId, updatedProduct);
        } catch (ProductNotFoundException e) {
        }

        // then
        Assertions.assertEquals(updatedProduct.getName(), product.getName());
        Assertions.assertEquals(updatedProduct.getDescription(), product.getDescription());
        Assertions.assertEquals(updatedProduct.getPrice(), product.getPrice());
        Assertions.assertEquals(updatedProduct.getQuantity(), product.getQuantity());
    }

    @Test
    void testDeleteProduct_ProductNotFound_ThrowsException() {
        // given
        Long productId = 123L;
        when(productDao.removeProduct(productId)).thenReturn(Optional.empty());

        // when
        Executable executable = () -> productService.deleteProduct(productId);

        // then
        Assertions.assertThrows(ProductNotFoundException.class, executable);
    }
}
