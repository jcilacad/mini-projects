package exception;

import com.ilacad.exception.InvalidEmailOrPasswordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InvalidEmailOrPasswordExceptionTest {

    @Test
    void testInvalidEmailOrPasswordException() {
        InvalidEmailOrPasswordException invalidEmailOrPasswordException =new InvalidEmailOrPasswordException("message");
        Assertions.assertEquals("message", invalidEmailOrPasswordException.getMessage());
    }
}
