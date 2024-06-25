package exception;

import com.ilacad.exception.InvalidIsbnException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class InvalidIsbnExceptionTest {

    @Test
    void testInvalidIsbnException() {
        InvalidIsbnException invalidIsbnException = new InvalidIsbnException("Invalid ISBN");
        assertEquals("Invalid ISBN format for ISBN: Invalid ISBN", invalidIsbnException.getMessage());
    }
}
