package service;

import com.ilacad.entity.Book;
import com.ilacad.exception.BookAlreadyExistsException;
import com.ilacad.exception.BookNotFoundException;
import com.ilacad.exception.InvalidIsbnException;
import com.ilacad.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void testAddBookValidObject() {
        // given
        /**
         * Examples of Valid ISBN
         * ISBN-10 - 0-306-40615-2
         * ISBN-13 - 978-3-16-148410-0
         */

        Book book = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");

        // when
        Book savedBook = bookService.addBook(book);

        // then
        assertEquals(book.getTitle(), savedBook.getTitle());
        assertEquals(book.getAuthor(), savedBook.getAuthor());
        assertEquals(book.getISBN(), savedBook.getISBN());
    }

    @Test
    void testAddBookInvalidIsbn() {
        // given
        Book book = new Book("Pride and Prejudice", "Jane Austen", "Invalid ISBN");

        // when
        Executable addBookAction = () -> bookService.addBook(book);

        // then
        Assertions.assertThrows(InvalidIsbnException.class, addBookAction);
    }

    @Test
    void testAddBookAlreadyExists() {
        // given
        Book book = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        bookService.addBook(book);

        // when
        Book existingBook = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        Executable executable = () -> bookService.addBook(existingBook);

        // then
        assertThrows(BookAlreadyExistsException.class, executable);
    }

    @Test
    void testFindAllBooks() {
        // given
        Book book1 = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        Book book2 = new Book("Crime and Punishment", "Fyodor Dostoevsky", "978-3-16-148410-0");
        bookService.addBook(book1);
        bookService.addBook(book2);

        // when
        List<Book> bookList = bookService.findAllBooks();

        // then
        assertEquals(2, bookList.size());
        assertEquals("Pride and Prejudice", bookList.get(0).getTitle());
        assertEquals("Jane Austen", bookList.get(0).getAuthor());
        assertEquals("0-306-40615-2", bookList.get(0).getISBN());
        assertEquals("Crime and Punishment", bookList.get(1).getTitle());
        assertEquals("Fyodor Dostoevsky", bookList.get(1).getAuthor());
        assertEquals("978-3-16-148410-0", bookList.get(1).getISBN());
    }

    @Test
    void testFindBookByIsbn() {
        // given
        Book book = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        bookService.addBook(book);

        // when
        Book existingBook = bookService.findBookByIsbn("0-306-40615-2");

        // then
        assertEquals(book.getTitle(), existingBook.getTitle());
        assertEquals(book.getAuthor(), existingBook.getAuthor());
        assertEquals(book.getISBN(), existingBook.getISBN());
    }

    @Test
    void testFindBookByIsbnNotFound() {
        // given
        String nonExistentIsbn = "978-3-16-148410-0";
        Book book = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        bookService.addBook(book);

        // when
        Executable executable = () -> bookService.findBookByIsbn(nonExistentIsbn);

        // then
        assertThrows(BookNotFoundException.class, executable);
    }

    @Test
    void testFindBooksByTitle() {
        // given
        Book book1 = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        Book book2 = new Book("Pride and Prejudice", "Jane Doe", "978-3-16-148410-0");
        bookService.addBook(book1);
        bookService.addBook(book2);

        // when
        List<Book> existingBook = bookService.findBooksByTitle("Pride and Prejudice");

        // then
        assertEquals(2, existingBook.size());
        assertEquals("Pride and Prejudice", existingBook.get(0).getTitle());
        assertEquals("Jane Austen", existingBook.get(0).getAuthor());
        assertEquals("0-306-40615-2", existingBook.get(0).getISBN());
        assertEquals("Pride and Prejudice", existingBook.get(1).getTitle());
        assertEquals("Jane Doe", existingBook.get(1).getAuthor());
        assertEquals("978-3-16-148410-0", existingBook.get(1).getISBN());
    }

    @Test
    void testFindBooksByTitleNotFound() {
        // given
        String nonExistentTitle = "Non Existent Title";
        Book book1 = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        Book book2 = new Book("Pride and Prejudice", "Jane Doe", "978-3-16-148410-0");
        bookService.addBook(book1);
        bookService.addBook(book2);

        // when
        Executable executable = () -> bookService.findBooksByTitle(nonExistentTitle);

        // then
        assertThrows(BookNotFoundException.class, executable);
    }

    @Test
    void testFindBooksByAuthor() {
        // given
        String nonExistentAuthor = "Non Existent Author";
        Book book1 = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        Book book2 = new Book("Pride and Prejudice", "Jane Doe", "978-3-16-148410-0");
        bookService.addBook(book1);
        bookService.addBook(book2);

        // when
        Executable executable = () -> bookService.findBooksByAuthor(nonExistentAuthor);

        // then
        assertThrows(BookNotFoundException.class, executable);
    }

    @Test
    void testDeleteBook() {
        // given
        Book book = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        bookService.addBook(book);

        // when
        bookService.deleteBook(book.getISBN());

        // then
        assertThrows(BookNotFoundException.class, () -> bookService.findBookByIsbn(book.getISBN()));
    }

    @Test
    void testDeleteBookNotFound() {
        // given
        String nonExistentIsbn = "978-3-16-148410-0";
        Book book = new Book("Pride and Prejudice", "Jane Austen", "0-306-40615-2");
        bookService.addBook(book);

        // when
        Executable executable = () -> bookService.deleteBook(nonExistentIsbn);

        // then
        assertThrows(BookNotFoundException.class, executable);
    }
}
