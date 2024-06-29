package exception;

import com.ilacad.exception.PasswordMismatchException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordMismatchExceptionTest {

    @Test
    void testPasswordMismatchException() {
        PasswordMismatchException passwordMismatchException = new PasswordMismatchException("message");
        Assertions.assertEquals("message", passwordMismatchException.getMessage());
    }
}
