package com.ilacad.service.impl;

import com.ilacad.exception.BookNotFoundException;
import com.ilacad.exception.InvalidIsbnException;
import com.ilacad.service.BookService;
import com.ilacad.entity.Book;
import com.ilacad.exception.BookAlreadyExistsException;
import org.apache.commons.validator.routines.ISBNValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final List<Book> books = new ArrayList<>();
    private final ISBNValidator isbnValidator = new ISBNValidator();
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public Book addBook(Book book) {
        boolean isBookExists = books.stream().anyMatch(b -> b.getISBN().equalsIgnoreCase(book.getISBN()));
        if (isBookExists) {
            throw new BookAlreadyExistsException(book.getISBN());
        }
        if (!isbnValidator.isValid(book.getISBN())) {
            throw new InvalidIsbnException(book.getISBN());
        }

        log.info("Adding book with ISBN: {}", book.getISBN());
        books.add(book);
        return book;
    }

    @Override
    public List<Book> findAllBooks() {
        log.info("Finding all books");
        return books;
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getISBN().equalsIgnoreCase(isbn))
                .findAny();

        if (foundBook.isEmpty()) {
            throw new BookNotFoundException("isbn", isbn);
        }

        log.info("Finding book with ISBN: {}", isbn);
        return books.stream()
                .filter(book -> book.getISBN().equalsIgnoreCase(isbn))
                .findAny()
                .orElseThrow(() -> new BookNotFoundException("isbn", isbn));
    }

    @Override
    public List<Book> findBooksByTitle(String title) {
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findAny();

        if (foundBook.isEmpty()) {
            throw new BookNotFoundException("title", title);
        }

        log.info("Finding book/s with title: {}", title);
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .findAny();

        if (foundBook.isEmpty()) {
            throw new BookNotFoundException("author", author);
        }

        log.info("Finding book/s with author: {}", author);
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    @Override
    public void deleteBook(String isbn) {
        Book foundBook = books.stream()
                .filter(book -> book.getISBN().equalsIgnoreCase(isbn))
                .findAny()
                .orElseThrow(() -> new BookNotFoundException("isbn", isbn));

        log.info("Deleting book with ISBN: {}", isbn);
        books.remove(foundBook);
    }
}
