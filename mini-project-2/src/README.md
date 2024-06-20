# Library Management System (Basic Version)

A simple Java project for managing a library's book collection.

## Installation

1. Clone the repository.
2. Compile the Java files using `javac`.
3. Run the program with `java Main`.

## Usage

- Add books: Use the `addBook` method.
- Remove books: Use the following options:
    1) Delete Book by Index
    2) Delete Book by Title
    3) Delete Book by Author
    4) Delete Book by ISBN
- Find books: Use the following options:
    1) Find Book by Index
    2) Find Book by Title
    3) Find Book by Author
    4) Find Book by ISBN
- Get a list of all books: Use the `findAllBooks` method.

## Project Structure

- `Main.java`: Entry point of the program.
- `Book.java`: Represents a book with author and ISBN. (Child Class)
- `Publication.java`: Represents a book with title (Parent Class)
