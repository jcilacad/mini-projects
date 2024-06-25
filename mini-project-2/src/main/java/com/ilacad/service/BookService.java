package com.ilacad.service;

import com.ilacad.entity.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    List<Book> findAllBooks();

    Book findBookByIsbn(String isbn);

    List<Book> findBooksByTitle(String title);

    List<Book> findBooksByAuthor(String author);

    void deleteBook(String isbn);
}
