# Library Management System (Basic Version)

A simple Java project for managing a library's book collection.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed **Java v17**
- You have installed **Maven (mvn)**

## Running Mini Project 2 Locally

Follow these steps to get the application running on your machine:

1. **Clone the application**

Use the following command to clone the application:
```bash
git clone https://github.com/jcilacad/mini-projects.git
```

2. **Navigate to the `mini-project-2` package.**

3. **Run as standalone**

Use the following command to run the application:
```bash
mvn clean install exec:java
```

## Usage

- **Add books**: Use the `addBook` method.
- **Get a list of all books**: Use the `findAllBooks` method.
- **Find book/s**: Use the following options:
    1) Find Book/s by Title
    2) Find Book/s by Author
    3) Find Book by ISBN
- **Remove book**: Use the `deleteBook` method.

## Project Structure

- `entity` Package: This is where you can find the data models.
- `exception` Package: Customized exceptions are located here.
- `service` Pacakge: Contains the business logic of the application.
- `Main` Class: The entry point of the program.

## Run unit tests

Use the following command to run the unit tests:
```bash
mvn verify
```
