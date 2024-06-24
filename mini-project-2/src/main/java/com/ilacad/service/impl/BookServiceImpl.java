package com.ilacad.service.impl;

import com.ilacad.exception.BookNotFoundException;
import com.ilacad.service.BookService;
import com.ilacad.entity.Book;
import com.ilacad.exception.BookAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final List<Book> books = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @Override
    public Book addBook(Book book) {
        boolean isBookExists = books.stream().anyMatch(b -> b.getISBN().equalsIgnoreCase(book.getISBN()));
        if (isBookExists) {
            throw new BookAlreadyExistsException(book.getISBN());
        }
        log.info("Adding book with ISBN: {}", book.getISBN());
        books.add(book);
        return book;
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        log.info("Finding book/s with title: {}", title);
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findAny();

        if (foundBook.isEmpty()) {
            throw new BookNotFoundException("title", title);
        }
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .toList();
    }

    @Override
    public List<Book> findBookByAuthor(String author) {
        log.info("Finding book/s with author: {}", author);
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .findAny();

        if (foundBook.isEmpty()) {
            throw new BookNotFoundException("author", author);
        }
        return books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .toList();
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        log.info("Finding book with ISBN: {}", isbn);
        Optional<Book> foundBook = books.stream()
                .filter(book -> book.getISBN().equalsIgnoreCase(isbn))
                .findAny();

        if (foundBook.isEmpty()) {
            throw new BookNotFoundException("isbn", isbn);
        }
        return books.stream()
                .filter(book -> book.getISBN().equalsIgnoreCase(isbn))
                .findAny()
                .orElseThrow(() -> new BookNotFoundException("isbn", isbn));
    }

    @Override
    public void deleteBook(String isbn) {
        log.info("Deleting book with ISBN: {}", isbn);
        Book foundBook = books.stream()
                .filter(book -> book.getISBN().equalsIgnoreCase(isbn))
                .findAny()
                .orElseThrow(() -> new BookNotFoundException("isbn", isbn));
        books.remove(foundBook);
    }

    @Override
    public List<Book> getAllBooks() {
        return books;
    }
}
