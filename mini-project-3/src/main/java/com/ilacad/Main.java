package com.ilacad;

import com.ilacad.bootstrap.AdminSeeder;
import com.ilacad.controller.AuthenticationController;
import com.ilacad.controller.CartController;
import com.ilacad.controller.ProductController;
import com.ilacad.dao.impl.CartDaoImpl;
import com.ilacad.dao.impl.ProductDaoImpl;
import com.ilacad.dao.impl.UserDaoImpl;
import com.ilacad.dto.OperationDto;
import com.ilacad.dto.ProductDto;
import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.LoginResponse;
import com.ilacad.dto.response.RegisterResponse;
import com.ilacad.entity.Product;
import com.ilacad.enums.Role;
import com.ilacad.service.impl.CartServiceImpl;
import com.ilacad.service.impl.ProductServiceImpl;
import com.ilacad.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class Main {
    private static final AuthenticationController authenticationController = new AuthenticationController(new UserServiceImpl(new UserDaoImpl()));
    private static final ProductController productController = new ProductController(new ProductServiceImpl(new ProductDaoImpl()));
    private static final CartController cartController = new CartController(new CartServiceImpl(new CartDaoImpl(), new UserDaoImpl(), new ProductDaoImpl()));
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminSeeder adminSeeder = new AdminSeeder(new UserDaoImpl());

    public static void main(String[] args) {

        adminSeeder.createAdmin();

        while (true) {
            System.out.println("""
                                    
                    =====================================================================
                                           Welcome to E-Commerce System
                                      Developed by: John Christopher Ilacad
                    =====================================================================
                                    
                                                [1] Login
                                                [2] Register
                                                [3] Exit
                                    
                                    
                    """);

            OperationDto operationDto = inputOperation(3);
            if (operationDto.isValid()) continue;

            if (operationDto.getOperation() == 3) break;
            process(operationDto.getOperation(), Role.DEFAULT, null);
        }

        System.out.println("""
                ===============================================
                                  Good Bye!
                ===============================================
                 """);
    }


    private static void adminView(LoginResponse loginResponse) {
        String email = loginResponse.getEmail();
        String firstName = loginResponse.getFirstName();
        String lastName = loginResponse.getLastName();
        String role = loginResponse.getRole().name();
        while (true) {
            System.out.printf("""
                    ===================================================================================
                    Name: %s %s                                                      
                    Email: %s
                    Role: %s
                                    
                                                       Welcome Admin  
                                    
                                                    [1] Add Product
                                                    [2] Update Product
                                                    [3] View Products
                                                    [4] Delete Product 
                                                    [5] Logout
                                    
                                    
                    ===================================================================================
                    """, firstName, lastName, email, role);
            OperationDto operationDto = inputOperation(5);
            if (operationDto.isValid()) continue;

            if (operationDto.getOperation() == 5) break;
            process(operationDto.getOperation(), Role.ADMIN, null);
        }
    }

    private static void userView(LoginRequest loginRequest) {
        while (true) {
            LoginResponse loginResponse = authenticationController.login(loginRequest);
            String email = loginResponse.getEmail();
            String firstName = loginResponse.getFirstName();
            String lastName = loginResponse.getLastName();
            String role = loginResponse.getRole().name();
            BigDecimal credits = loginResponse.getCredits();
            System.out.printf("""
                    ===================================================================================
                    Name: %s %s                                                      
                    Email: %s
                    Role: %s
                    Credits: %s                                    
                                                       Welcome User  
                                    
                                                    [1] View Available Products
                                                    [2] View Products From Cart
                                                    [3] Add to Cart
                                                    [4] Remove to Cart 
                                                    [5] Checkout
                                                    [6] Add Credits
                                                    [7] Logout
                                    
                                    
                    ===================================================================================
                    """, firstName, lastName, email, role, credits);

            OperationDto operationDto = inputOperation(7);
            if (operationDto.isValid()) continue;

            if (operationDto.getOperation() == 7) break;
            process(operationDto.getOperation(), Role.USER, loginResponse.getEmail());
        }
    }

    private static void process(int operation, Role role, String userEmail) {
        if (Role.ADMIN.equals(role)) {
            switch (operation) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    getAllProducts();
                    break;
                case 4:
                    deleteProduct();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            }
        } else if (Role.USER.equals(role)) {
            switch (operation) {
                case 1:
                    getAllProducts();
                    break;
                case 2:
                    getAllProductsFromCart(userEmail);
                    break;
                case 3:
                    addToCart(userEmail);
                    break;
                case 4:
                    removeToCart(userEmail);
                    break;
                case 5:
                    checkout(userEmail);
                    break;
                case 6:
                    addCredits(userEmail);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            }
        } else {
            switch (operation) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + operation);
            }
        }
    }

    private static void addProduct() {
        boolean addProductSuccessful = false;

        scanner.nextLine();
        while (!addProductSuccessful) {
            try {
                System.out.println("""
                        ===============================
                                  Add Product
                        ===============================        
                        """);
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();
                System.out.print("Enter product description: ");
                String productDescription = scanner.nextLine();
                System.out.print("Enter product price: ");
                BigDecimal productPrice = scanner.nextBigDecimal();
                System.out.print("Enter product quantity: ");
                int productQuantity = scanner.nextInt();
                ProductDto productDto = ProductDto.builder()
                        .name(productName)
                        .description(productDescription)
                        .price(productPrice)
                        .quantity(productQuantity)
                        .build();
                ProductDto savedProduct = productController.addProduct(productDto);
                log.info("Product added successfully with id: {}", savedProduct.getId());
                addProductSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    addProductSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void updateProduct() {
        boolean updateProductSuccessful = false;

        scanner.nextLine();
        while (!updateProductSuccessful) {
            try {
                System.out.println("""
                        ===============================
                                Update Product
                        ===============================        
                        """);
                System.out.print("Enter product id of an existing product: ");
                Long productId = scanner.nextLong();
                scanner.nextLine();
                System.out.print("Enter updated product name: ");
                String updatedProductName = scanner.nextLine();
                System.out.print("Enter updated product description: ");
                String updatedProductDescription = scanner.nextLine();
                System.out.print("Enter updated product price: ");
                BigDecimal updatedProductPrice = scanner.nextBigDecimal();
                System.out.print("Enter updated product quantity: ");
                int updatedProductQuantity = scanner.nextInt();
                ProductDto productDto = ProductDto.builder()
                        .name(updatedProductName)
                        .description(updatedProductDescription)
                        .price(updatedProductPrice)
                        .quantity(updatedProductQuantity)
                        .build();
                ProductDto updatedProduct = productController.updateProduct(productId, productDto);
                log.info("Product updated successfully with id: {}", updatedProduct.getId());
                updateProductSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    updateProductSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void getAllProducts() {
        System.out.println("""
                ===============================
                        Get All Products
                ===============================        
                """);
        productController.getAllProducts()
                .stream()
                .forEach(productDto -> {
                    System.out.println(String.format("id: %s || name: %s || description: %s || price: %s || quantity: %s",
                            productDto.getId(),
                            productDto.getName(),
                            productDto.getDescription(),
                            productDto.getPrice(),
                            productDto.getQuantity()));
                });
    }

    private static void deleteProduct() {
        boolean deleteProductSuccessful = false;

        scanner.nextLine();
        while (!deleteProductSuccessful) {
            try {
                System.out.println("""
                        ===============================
                                 Delete Product
                        ===============================        
                        """);
                System.out.print("Enter product id of an existing product: ");
                Long productId = scanner.nextLong();
                productController.deleteProduct(productId);
                log.info("Product deleted successfully");
                deleteProductSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    deleteProductSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static BigDecimal[] getAllProductsFromCart(String userEmail) {
        final BigDecimal[] totalPrice = {BigDecimal.ZERO};
        try {
            System.out.println("""
                    ===========================================
                             Get All Products From Cart
                    ===========================================
                    """);
            List<Product> products = cartController.findAllProductsFromCart(userEmail);
            Map<Product, Integer> productsMap = new HashMap<>();

            products.forEach(p -> productsMap.put(p, productsMap.getOrDefault(p, 0) + 1));

            System.out.println("===================================================");
            productsMap.forEach((k, v) -> {
                System.out.println(String.format("ID: %s -- Name: %s  -- Quantity: %s", k.getId(), k.getName(), v));
                BigDecimal productTotal = k.getPrice().multiply(BigDecimal.valueOf(v));
                totalPrice[0] = totalPrice[0].add(productTotal);
            });
            System.out.println("===================================================");
            System.out.println(String.format("Total Price: %s", totalPrice[0]));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new BigDecimal[]{totalPrice[0]};
    }

    private static void addToCart(String userEmail) {
        boolean addToCartSuccessful = false;

        scanner.nextLine();
        while (!addToCartSuccessful) {
            try {
                int numberOfItems;
                getAllProducts();
                System.out.println("""
                        ===============================
                                  Add to Cart
                        ===============================        
                        """);
                System.out.print("Enter the product ID you want to add to the cart: ");
                Long productId = scanner.nextLong();
                System.out.print(String.format("How many of product ID %s do you want to add to the cart? ", productId));
                numberOfItems = scanner.nextInt();
                cartController.addToCart(userEmail, productId, numberOfItems);
                log.info("Product added to cart successfully");
                addToCartSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    addToCartSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void removeToCart(String userEmail) {
        boolean removeToCartSuccessful = false;

        scanner.nextLine();
        while (!removeToCartSuccessful) {
            try {
                int numberOfItems;
                System.out.println("""
                        ===============================
                                  Remove to Cart
                        ===============================        
                        """);
                getAllProductsFromCart(userEmail);
                System.out.print("Enter the product ID you want to remove to the cart: ");
                Long productId = scanner.nextLong();
                System.out.print(String.format("How many of product ID %s do you want to remove to the cart? ", productId));
                numberOfItems = scanner.nextInt();
                cartController.removeProductFromCart(userEmail, productId, numberOfItems);
                log.info("Product removed to cart successfully");
                removeToCartSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    removeToCartSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void checkout(String userEmail) {
        boolean checkoutSuccessful = false;

        scanner.nextLine();
        while (!checkoutSuccessful) {
            BigDecimal[] totalPrice = getAllProductsFromCart(userEmail);
            try {
                System.out.println("""
                        ===============================
                                   Checkout
                        ===============================        
                        """);
                System.out.print("Are you sure you want to checkout [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    cartController.checkout(userEmail, totalPrice);
                    log.info("Checked out the product successfully");
                }
                checkoutSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    checkoutSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void addCredits(String userEmail) {
        boolean addCreditsSuccessful = false;

        scanner.nextLine();
        while (!addCreditsSuccessful) {
            try {
                System.out.println("""
                        ===============================
                                  Add Credits
                        ===============================        
                        """);
                System.out.print("Enter credits: ");
                BigDecimal credits = scanner.nextBigDecimal();
                cartController.addCredits(userEmail, credits);
                log.info("Credits added successfully");
                addCreditsSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    addCreditsSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void login() {
        boolean loginSuccessful = false;

        scanner.nextLine();
        while (!loginSuccessful) {
            try {
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                LoginRequest loginRequest = LoginRequest.builder()
                        .email(email)
                        .password(password)
                        .build();

                LoginResponse loginResponse = authenticationController.login(loginRequest);
                log.info("Successfully logged in user with email: {}", loginRequest.getEmail());
                if (loginResponse.getRole().equals(Role.ADMIN)) {
                    adminView(loginResponse);
                } else {
                    userView(loginRequest);
                }
                loginSuccessful = true;
            } catch (Exception e) {
                log.error(e.getMessage());
                scanner.nextLine();
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    loginSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static void register() {
        boolean registrationSuccessful = false;

        scanner.nextLine();
        while (!registrationSuccessful) {
            try {
                System.out.print("Enter first name: ");
                String firstName = scanner.nextLine();
                System.out.print("Enter last name: ");
                String lastName = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.print("Confirm password: ");
                String confirmPassword = scanner.nextLine();

                RegisterRequest registerRequest = RegisterRequest.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(password)
                        .confirmPassword(confirmPassword)
                        .build();

                RegisterResponse response = authenticationController.register(registerRequest);
                log.info("Successfully registered user with email: {}", response.getEmail());
                registrationSuccessful = true;
            } catch (Exception e) {
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    registrationSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static OperationDto inputOperation(int max) {
        int operation = 0;
        boolean isValid = false;
        System.out.print("Enter operation: ");
        try {
            operation = scanner.nextInt();
        } catch (InputMismatchException e) {
            log.error("Invalid operation; must be a number only.");
            scanner.nextLine();
            isValid = true;
        }

        if (operation > max || operation < 1) {
            log.error("Invalid operation. The input must be a number within the range of 1 to 7. Operation entered: {}.", operation);
            isValid = true;
        }

        return new OperationDto(isValid, operation);
    }
}
