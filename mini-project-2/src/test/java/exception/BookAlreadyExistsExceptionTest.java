package exception;

import com.ilacad.exception.BookAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookAlreadyExistsExceptionTest {

    @Test
    void testBookAlreadyExistsException() {
        BookAlreadyExistsException bookAlreadyExistsException = new BookAlreadyExistsException("Book ISBN");
        assertEquals("Book already exists with isbn: Book ISBN", bookAlreadyExistsException.getMessage());
    }
}
