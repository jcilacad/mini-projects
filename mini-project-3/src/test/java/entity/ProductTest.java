package entity;

import com.ilacad.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @Test
    void testAllArgsConstructor() {
        Product product = new Product(
                1L, "name", "description", BigDecimal.ZERO, 1);
        assertEquals(product);
    }

    @Test
    void testNoArgsConstructor() {
        Product product = new Product();
        Assertions.assertInstanceOf(Product.class, product);
    }

    @Test
    void testSetters() {
        Product product = new Product();
        product.setId(1L);
        product.setName("name");
        product.setDescription("description");
        product.setPrice(BigDecimal.ZERO);
        product.setQuantity(1);
        assertEquals(product);
    }

    void assertEquals(Product product) {
        Assertions.assertEquals(1L, product.getId());
        Assertions.assertEquals("name", product.getName());
        Assertions.assertEquals("description", product.getDescription());
        Assertions.assertEquals(BigDecimal.ZERO, product.getPrice());
        Assertions.assertEquals(1, product.getQuantity());
    }
}
