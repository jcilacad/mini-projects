package exception;

import com.ilacad.exception.BookNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookNotFoundExceptionTest {

    @Test
    void testBookNotFoundException() {
        BookNotFoundException bookNotFoundException = new BookNotFoundException("field", "value");
        assertEquals("Book not found with field : value", bookNotFoundException.getMessage());
    }
}
