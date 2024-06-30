# E-commerce Cart System (Basic Version)

Java-based e-commerce system with role-based access control (RBAC).


## Prerequisites

Before you begin, ensure you have met the following requirements:
- **Java v17** is installed.
- **Maven (mvn)** is installed.
- **Postgres Server** is running.


## Getting Started

Follow these steps to get the application running on your machine:

1. **Clone the repository**

Use the following command to clone the application:
```bash
git clone https://github.com/jcilacad/mini-projects.git
```

2. **Setup your database configuration**

Create a database named "ecommerce_db" using the following command:
```sql
CREATE DATABASE ecommerce_db
```

3. **Navigate to the `mini-project-3` package.**

4. **Run as standalone**

Use the following command to run the application:
```bash
mvn clean install exec:java
```


## Usage

### Default Actions

- **Login**: Users can log in to the system.
- **Register** (Applicable to Buyers Only): Buyers can register their accounts.

### Admin / Seller Actions

- **Add Product**: Admins or sellers can add new products to the system.
- **Update Product**: Modify existing product details.
- **View Products**: Browse the available product collection.
- **Delete Product**: Remove product from the list.
- **Logout**: Exit the system.

### User / Buyer Actions

- **View Available Products**: See the list of available products.
- **View Products From Cart**: Review products in the cart.
- **Add to Cart**: Add product to the cart.
- **Remove from Cart**: Remove product from the cart.
- **Checkout**: Complete the purchase.
- **Add Credits**: Add credits to the account.
- **Logout**: Log out of the system.


## Project Structure

The project follows a well-organized structure to manage its components effectively:

- `bootstrap` Package: Contains necessary code to bootstrap the application.
- `entity` Package: Contains data models representing entities.
- `exception` Package: Customized exceptions are located here to handle specific scenarios.
- `service` Package: Contains the business logic of the application.
- `dao` Package: Handles data access and database interactions.
- `dto` Package: Contains data transfer objects used for communication between layers.
- `enums` Package: Contains enums representing different categories or types.
- `mapper` Package: Includes mapper interfaces for converting between entity objects and DTOs.
- `util` Package: Contains utility classes or helper methods used throughout the application.
- `Main Class`: The entry point of the program where execution begins.


## Run unit tests

Use the following command to run the unit tests:
```bash
mvn verify
```
