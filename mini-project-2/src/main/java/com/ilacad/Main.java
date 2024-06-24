package com.ilacad;

import com.ilacad.entity.Book;
import com.ilacad.exception.BookAlreadyExistsException;
import com.ilacad.exception.BookNotFoundException;
import com.ilacad.exception.InvalidIsbnException;
import com.ilacad.service.BookService;
import com.ilacad.service.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static BookService bookService = new BookServiceImpl();

    public Main(BookService bookService) {
        this.bookService = bookService;
    }

    public static void main(String[] args) {

        int operation;
        do {
            System.out.print("""
                                    
                    ==================================================================
                                   Welcome to Library Management System
                                   Developed by: John Christopher Ilacad
                    ==================================================================
                                    
                                    [1] Add Book
                                    [2] Search All Books
                                    [3] Search Book by Title
                                    [4] Search Book by Author
                                    [5] Search Book by ISBN
                                    [6] Delete Book by ISBN 
                                    [7] Exit
                                
                    ==================================================================                                    
                    """);
            System.out.print("Choose an operation: ");
            operation = scanner.nextInt();
            process(operation);
        } while (operation != 7);

        System.out.println("""
                ===============================================
                                  Good Bye!
                ===============================================
                 """);
    }

    private static void process(int operation) {

        switch (operation) {
            case 1:
                addBook();
                break;
            case 2:
                findAllBooks();
                break;
            case 3:
                findBookByTitle();
                break;
            case 4:
                findBookByAuthor();
                break;
            case 5:
                findBookByIsbn();
                break;
            case 6:
                deleteBookByIsbn();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
    }

    private static void addBook() {

        System.out.println("""
                ==================================
                            Add Book
                ==================================  
                """);
        scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        Book savedBook;
        try {
            savedBook = bookService.addBook(new Book(title, author, isbn));
            log.info("Book added successfully with isbn: {}", savedBook.getISBN());
        } catch (BookAlreadyExistsException e) {
            log.error("Book already exists with isbn: {}", isbn);
        } catch (InvalidIsbnException e) {
            log.error("Invalid isbn format for isbn: {}", isbn);
        }
    }

    private static void findAllBooks() {
        System.out.println("""
                ==================================
                            Find All Books
                ==================================  
                """);
        bookService.getAllBooks()
                .stream()
                .forEach(System.out::println);
    }

    private static void findBookByTitle() {
        System.out.println("""
                ==================================
                         Find Book/s by Title
                ==================================  
                """);
        scanner.nextLine();
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        try {
            List<Book> foundBooks = bookService.findBookByTitle(title);
            log.info("Book/s found with title: {}", title);
            foundBooks.stream()
                    .forEach(System.out::println);
        } catch (BookNotFoundException e) {
            log.error("Book/s not found with with title: {}", title);
        }
    }

    private static void findBookByAuthor() {
        System.out.println("""
                ==================================
                         Find Book/s by Author
                ==================================  
                """);
        scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        try {
            List<Book> foundBooks = bookService.findBookByAuthor(author);
            log.info("Book/s found with author: {}", author);
            foundBooks.stream()
                    .forEach(System.out::println);
        } catch (BookNotFoundException e) {
            log.error("Book/s not found with author: {}", author);
        }
    }

    private static void findBookByIsbn() {
        System.out.println("""
                ==================================
                         Find Book by ISBN
                ==================================  
                """);
        scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        try {
            Book foundBook = bookService.findBookByIsbn(isbn);
            log.info("Book found with isbn: {}", foundBook.getISBN());
        } catch (BookNotFoundException e) {
            log.error("Book not found with isbn: {}", isbn);
        }
    }

    private static void deleteBookByIsbn() {
        System.out.println("""
                ==================================
                         Delete Book by ISBN
                ==================================  
                """);
        scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        try {
            bookService.deleteBook(isbn);
            log.info("Book deleted successfully with isbn: {}", isbn);
        } catch (BookNotFoundException e) {
            log.error("Book not found with isbn: {}", isbn);
        }
    }
}