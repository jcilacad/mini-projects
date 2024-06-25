package entity;

import com.ilacad.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookTest {

    @Test
    void testAllArgsConstructor() {
        Book book = new Book("Book Title", "Book Author", "Book ISBN");
        assertEquals(book);
    }

    @Test
    void testNoArgsConstructor() {
        Book book = new Book();
        Assertions.assertInstanceOf(Book.class, book);
    }

    @Test
    void testSetters() {
        Book book = new Book();
        book.setTitle("Book Title");
        book.setAuthor("Book Author");
        book.setISBN("Book ISBN");
        assertEquals(book);
    }

    void assertEquals(Book book) {
        Assertions.assertEquals("Book Title", book.getTitle());
        Assertions.assertEquals("Book Author", book.getAuthor());
        Assertions.assertEquals("Book ISBN", book.getISBN());
    }
}
