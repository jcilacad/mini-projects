import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Book> books = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("""
                    =============== Select an operation ===============
                    1) Add Book
                    2) Delete Book by Index
                    3) Delete Book by Title
                    4) Delete Book by Author                
                    5) Delete Book by ISBN
                    6) Find Book by Index
                    7) Find Book by Title
                    8) Find Book by Author
                    9) Find Book by ISBN
                    10) Find All Books
                    11) Exit
                    
                    ===================================================
                    """);

            System.out.print("Enter operation: ");
            int response = new Scanner(System.in).nextInt();
            if (response == 11) break;
            process(response);
        }

    }

    private static void addBook(Book book) {
        books.add(book);
        System.out.println(String.format("Book with title: %s, author: %s, and ISBN: %s, successfully created",
                book.getTitle(), book.getAuthor(), book.getISBN()));
    }

    private static void deleteBookByIndex(int index) {
        books.remove(index);
        System.out.println(String.format("Book deleted with index: %d ", index));
    }

    private static void deleteBookByTitle(String title) {
        List<Book> foundBooks = books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).toList();
        books.removeAll(foundBooks);
        System.out.println(String.format("Books deleted with title: %s", title));
    }

    private static void deleteBookByAuthor(String author) {
        List<Book> foundBooks = books.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).toList();
        books.removeAll(foundBooks);
        System.out.println(String.format("Books deleted with author: %s", author));
    }

    private static void deleteBookByISBN(String ISBN) {
        List<Book> foundBooks = books.stream().filter(book -> book.getISBN().equalsIgnoreCase(ISBN)).toList();
        books.removeAll(foundBooks);
        System.out.println(String.format("Books deleted with ISBN: %s", ISBN));
    }

    private static void findBookByIndex(int index) {
        Book book = books.get(index);
        System.out.println("Book Found!");
        System.out.println(book);
    }

    private static void findBookByTitle(String title) {
        List<Book> foundBooks = books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).toList();
        System.out.println("Book/s Found!");
        foundBooks.forEach(System.out::println);
    }

    private static void findBookByAuthor(String author) {
        List<Book> foundBooks = books.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).toList();
        System.out.println("Book/s Found!");
        foundBooks.forEach(System.out::println);
    }

    private static void findBookByISBN(String ISBN) {
        List<Book> foundBooks = books.stream().filter(book -> book.getISBN().equalsIgnoreCase(ISBN)).toList();
        System.out.println("Book/s Found!");
        foundBooks.forEach(System.out::println);
    }

    private static void findAllBooks() {
        books.forEach(System.out::println);
    }

    private static void process(int response) {
        switch (response) {
            case 1:
                System.out.println("=============Add book=============");
                System.out.print("Enter Title: ");
                String title = scanner.nextLine();
                System.out.print("Enter Author: ");
                String author = scanner.nextLine();
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                addBook(new Book(title, author, isbn));
                break;
            case 2:
                System.out.println("=============Delete book by index=============");
                System.out.print("Enter Index: ");
                int deleteIndex = scanner.nextInt();
                deleteBookByIndex(deleteIndex);
                break;
            case 3:
                System.out.println("=============Delete book by title=============");
                System.out.print("Enter title: ");
                String deleteTitle = scanner.nextLine();
                deleteBookByTitle(deleteTitle);
                break;
            case 4:
                System.out.println("=============Delete book by author=============");
                System.out.print("Enter author: ");
                String deleteAuthor = scanner.nextLine();
                deleteBookByTitle(deleteAuthor);
                break;
            case 5:
                System.out.println("=============Delete book by ISBN=============");
                System.out.print("Enter ISBN: ");
                String deletedIsbn = scanner.nextLine();
                deleteBookByTitle(deletedIsbn);
                break;
            case 6:
                System.out.println("=============Find book by index=============");
                System.out.print("Enter Index: ");
                int findIndex = scanner.nextInt();
                findBookByIndex(findIndex);
                break;
            case 7:
                System.out.println("=============Find book by title=============");
                System.out.print("Enter Title: ");
                String findTitle = scanner.nextLine();
                findBookByTitle(findTitle);
                break;
            case 8:
                System.out.println("=============Find book by author=============");
                System.out.print("Enter Author: ");
                String findAuthor = scanner.nextLine();
                findBookByAuthor(findAuthor);
                break;
            case 9:
                System.out.println("=============Find book by ISBN=============");
                System.out.print("Enter ISBN: ");
                String findIsbn = scanner.nextLine();
                findBookByISBN(findIsbn);
                break;
            case 10:
                System.out.println("=============Find all books=============");
                findAllBooks();
                break;
        }
    }
}
