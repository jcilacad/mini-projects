package exception;

import com.ilacad.exception.ProductAlreadyExistsException;
import com.ilacad.exception.ProductQuantityExceededException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductQuantityExceededExceptionTest {

    @Test
    void testProductQuantityExceededException() {
        ProductQuantityExceededException productQuantityExceededException = new ProductQuantityExceededException("message");
        Assertions.assertEquals("message", productQuantityExceededException.getMessage());
    }
}
