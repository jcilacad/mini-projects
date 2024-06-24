import com.ilacad.Main;
import com.ilacad.entity.Book;
import com.ilacad.service.BookService;
import com.ilacad.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainTest {

    private static final Logger log = LoggerFactory.getLogger(MainTest.class);
    private final BookService bookService = new BookServiceImpl();

    @Test
    public void givenBookObject_whenAddBook_thenReturnBookObject() {
        // given
        Book book = new Book("Book title", "Book Author", "Book ISBN");

        // when
        Book savedBook = bookService.addBook(book);

        // then

    }
}
