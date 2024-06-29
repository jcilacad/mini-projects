package exception;

import com.ilacad.exception.CartNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CartNotFoundExceptionTest {

    @Test
    void testCartNotFoundException() {
        CartNotFoundException cartNotFoundException = new CartNotFoundException(1L);
        Assertions.assertEquals("Cart not found with id: 1", cartNotFoundException.getMessage());
    }
}
