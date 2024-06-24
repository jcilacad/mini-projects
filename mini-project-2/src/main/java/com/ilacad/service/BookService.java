package com.ilacad.service;

import com.ilacad.entity.Book;

import java.util.List;

public interface BookService {

    Book addBook(Book book);

    List<Book> findBookByTitle(String title);

    List<Book> findBookByAuthor(String author);

    Book findBookByIsbn(String isbn);

    void deleteBook(String isbn);

    List<Book> getAllBooks();
}
