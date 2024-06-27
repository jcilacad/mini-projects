package com.ilacad;

import com.ilacad.bootstrap.AdminSeeder;
import com.ilacad.controller.AuthenticationController;
import com.ilacad.dao.impl.UserDaoImpl;
import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.RegisterResponse;
import com.ilacad.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

@RequiredArgsConstructor
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final AuthenticationController authenticationController = new AuthenticationController(new UserServiceImpl(new UserDaoImpl()));
    private static final Scanner scanner = new Scanner(System.in);
    private static final AdminSeeder adminSeeder = new AdminSeeder(new UserDaoImpl());

    public static void main(String[] args) {

        adminSeeder.createAdmin();

        while (true) {
            int operation;
            System.out.println("""
                                    
                    =====================================================================
                                           Welcome to E-Commerce System
                                      Developed by: John Christopher Ilacad
                    =====================================================================
                                    
                                                [1] Login
                                                [2] Register
                                                [3] Exit
                                    
                                    
                    """);

            System.out.print("Enter operation: ");
            try {
                operation = scanner.nextInt();
            } catch (InputMismatchException e) {
                log.error("Invalid operation; must be a number only.");
                scanner.nextLine();
                continue;
            }

            if (operation > 3 || operation < 1) {
                log.error("Invalid operation. The input must be a number within the range of 1 to 7. Operation entered: {}.", operation);
                continue;
            }

            if (operation == 3) break;
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
                login();
                break;
            case 2:
                register();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
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

                authenticationController.login(loginRequest);
                System.out.println(String.format("Successfully logged in user with email: %s", loginRequest.getEmail()));
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
                log.error(e.getMessage());
                System.out.print("\n\nDo you want to exit [Y/N]: ");
                char answer = scanner.next().charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    registrationSuccessful = true;
                }
                scanner.nextLine();
            }
        }
    }

    private static boolean isUserValid() {
        return false;
    }
}
