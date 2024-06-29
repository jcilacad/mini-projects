package exception;

import com.ilacad.exception.ProductAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductAlreadyExistsExceptionTest {

    @Test
    void testProductAlreadyExistsException() {
        ProductAlreadyExistsException productAlreadyExistsException = new ProductAlreadyExistsException("product name");
        Assertions.assertEquals("Product already exists with name: product name", productAlreadyExistsException.getMessage());
    }
}
