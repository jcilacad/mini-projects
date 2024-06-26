package com.ilacad;

import com.ilacad.entity.Book;
import com.ilacad.exception.BookAlreadyExistsException;
import com.ilacad.exception.BookNotFoundException;
import com.ilacad.exception.InvalidIsbnException;
import com.ilacad.service.BookService;
import com.ilacad.service.impl.BookServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
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

        while (true) {
            int operation;
            System.out.print("""
                                    
                    ==================================================================
                                   Welcome to Library Management System
                                   Developed by: John Christopher Ilacad
                    ==================================================================
                                    
                                    [1] Add Book
                                    [2] Search All Books
                                    [3] Search Book by ISBN
                                    [4] Search Book/s by Title/Author
                                    [5] Delete Book by ISBN 
                                    [6] Exit
                                
                    ==================================================================                                    
                    """);
            System.out.print("Choose an operation: ");
            try {
                operation = scanner.nextInt();
            } catch (InputMismatchException e) {
                log.error("Invalid operation; must be a number only.");
                scanner.nextLine();
                continue;
            }

            if (operation > 6 || operation < 1) {
                log.error("Invalid operation. The input must be a number within the range of 1 to 7. Operation entered: {}.", operation);
                continue;
            }

            if (operation == 6) break;
            process(operation);
        }

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
                findBookByIsbn();
                break;
            case 4:
                findBooksByField();
                break;
            case 5:
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
            log.error(e.getMessage());
        } catch (InvalidIsbnException e) {
            log.error(e.getMessage());
        }
    }

    private static void findAllBooks() {
        System.out.println("""
                ==================================
                            Find All Books
                ==================================  
                \n""");
        bookService.findAllBooks()
                .stream()
                .forEach(System.out::println);
    }

    private static void findBookByIsbn() {
        System.out.println("""
                ==================================
                         Find Book by ISBN
                ==================================  
                \n""");
        scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        try {
            System.out.println("\n");
            Book foundBook = bookService.findBookByIsbn(isbn);
            log.info("Book found with isbn: {}", foundBook.getISBN());
            System.out.println(foundBook);
        } catch (BookNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    private static void findBooksByField() {
        int operation;
        StringBuilder field = new StringBuilder();
        while (true) {
            System.out.println("""
                    ==================================
                             Find Book/s by Field
                    ==================================
                                    
                                [1] Title
                                [2] Author
                                [3] Exit
                              
                    \n""");

            System.out.print("Choose an operation (1-3): ");
            try {
                operation = scanner.nextInt();
            } catch (InputMismatchException e) {
                log.error("Invalid operation; must be a number only.");
                scanner.nextLine();
                continue;
            }

            if (operation > 3 || operation < 1) {
                log.error("Invalid operation. The input must be a number within the range of 1 to 3. Operation entered: {}.", operation);
                continue;
            }

            if (operation == 3) break;

            if (operation == 1) field.append("title");
            else if (operation == 2) field.append("author");

            scanner.nextLine();
            System.out.print("Enter value: ");
            String value = scanner.nextLine();
            try {
                List<Book> foundBooks = bookService.findBooksByField(field.toString(), value);
                log.info("Book/s found with {}: {}", field, value);
                System.out.println("\n");
                foundBooks.stream()
                        .forEach(System.out::println);
                field.setLength(0);
            } catch (BookNotFoundException e) {
                field.setLength(0);
                log.error(e.getMessage());
            }
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
            log.error(e.getMessage());
        }
    }
}