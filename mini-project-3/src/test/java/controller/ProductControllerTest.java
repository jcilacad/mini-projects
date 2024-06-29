package controller;

import com.ilacad.controller.ProductController;
import com.ilacad.dto.ProductDto;
import com.ilacad.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Test
    void testConstructor() {
        ProductController controller = new ProductController(productService);
        Assertions.assertInstanceOf(ProductController.class, controller);
    }

    @Test
    void testAddProduct() {
        // given
        ProductDto productDto = ProductDto.builder()
                .name("product name")
                .description("product description")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();

        when(productService.addProduct(productDto)).thenReturn(productDto);

        // when
        ProductDto response = productController.addProduct(productDto);

        // then
        assertEquals(productDto.getName(), response.getName());
        assertEquals(productDto.getDescription(), response.getDescription());
        assertEquals(productDto.getPrice(), response.getPrice());
        assertEquals(productDto.getQuantity(), response.getQuantity());
    }

    @Test
    void testGetAllProducts() {
        // given
        ProductDto product1 = ProductDto.builder()
                .name("product 1")
                .description("description 1")
                .price(BigDecimal.ZERO)
                .quantity(1)
                .build();

        ProductDto product2 = ProductDto.builder()
                .name("product 2")
                .description("description 2")
                .price(BigDecimal.TEN)
                .quantity(2)
                .build();

        List<ProductDto> products = List.of(product1, product2);
        when(productService.getAllProducts()).thenReturn(products);

        // when
        List<ProductDto> productDtos = productController.getAllProducts();

        // then
        assertEquals(2, productDtos.size());
    }

    @Test
    void testUpdateProduct() {
        // given
        Long id = 1L;
        ProductDto updatedProductDto = ProductDto.builder()
                .name("updated product name")
                .description("updated product description")
                .price(BigDecimal.TEN)
                .quantity(10)
                .build();
        when(productService.updateProduct(id, updatedProductDto)).thenReturn(updatedProductDto);

        // when
        ProductDto updatedProduct = productController.updateProduct(id, updatedProductDto);

        // then
        assertEquals(updatedProductDto.getName(), updatedProduct.getName());
        assertEquals(updatedProductDto.getDescription(), updatedProduct.getDescription());
        assertEquals(updatedProductDto.getPrice(), updatedProduct.getPrice());
        assertEquals(updatedProductDto.getQuantity(), updatedProduct.getQuantity());
    }

    @Test
    void testDeleteProduct() {
        // given
        Long id = 1L;

        // when
        productController.deleteProduct(id);

        // then
        verify(productService).deleteProduct(id);
    }
}
