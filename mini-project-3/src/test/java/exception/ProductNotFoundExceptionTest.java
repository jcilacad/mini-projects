package exception;

import com.ilacad.exception.ProductNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductNotFoundExceptionTest {

    @Test
    void testProductNotFoundException() {
        ProductNotFoundException productNotFoundException = new ProductNotFoundException(1L);
        Assertions.assertEquals("Product not found with id: 1", productNotFoundException.getMessage());
    }
}
