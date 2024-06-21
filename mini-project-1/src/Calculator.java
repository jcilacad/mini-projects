import java.util.InputMismatchException;
import java.util.Scanner;

public class Calculator {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int firstNumber, secondNumber, anotherNumber, result = 0;
        char operation;

        while (true) {
            try {
                System.out.print("Enter first number: ");
                firstNumber = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, must be a number!");
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("Enter second number: ");
                secondNumber = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, must be a number!");
                scanner.nextLine();
            }
        }

        do {
            try {
                System.out.print("Enter an operation (+, -, *, /): ");
                operation = scanner.next().charAt(0);
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
                operation = '\0';
            }
        } while (operation != '+' && operation != '-' && operation != '*' && operation != '/');

        try {
            result = calculate(firstNumber, secondNumber, operation);
        } catch (ArithmeticException exception) {
            System.out.println(exception.getMessage());
        }

        while (true) {
            System.out.print("Do you want to calculate another number [Y/N]: ");
            char response = scanner.next().charAt(0);
            if (response == 'N' || response == 'n') break;

            do {
                try {
                    System.out.print("Enter an operation (+, -, *, /): ");
                    operation = scanner.next().charAt(0);
                } catch (Exception e) {
                    System.out.println("Invalid input. Please try again.");
                    scanner.nextLine();
                    operation = '\0';
                }
            } while (operation != '+' && operation != '-' && operation != '*' && operation != '/');

            while (true) {
                try {
                    System.out.print("Enter another number: ");
                    anotherNumber = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, must be a number!");
                    scanner.nextLine();
                }
            }

            try {
                result = calculate(result, anotherNumber, operation);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(String.format("The result is: %d", result));
    }

    private static int calculate(int firstNumber, int secondNumber, char operation) {
        int answer;
        switch (operation) {
            case '+':
                answer = addition(firstNumber, secondNumber);
                break;
            case '-':
                answer = subtraction(firstNumber, secondNumber);
                break;
            case '*':
                answer = multiplication(firstNumber, secondNumber);
                break;
            case '/':
                answer = division(firstNumber, secondNumber);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
        return answer;
    }

    private static int addition(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    private static int subtraction(int firstNumber, int secondNumber) {
        return firstNumber - secondNumber;
    }

    private static int multiplication(int firstNumber, int secondNumber) {
        return firstNumber * secondNumber;
    }

    private static int division(int firstNumber, int secondNumber) {
        if (secondNumber == 0) throw new ArithmeticException("Cannot divide by zero");
        return firstNumber / secondNumber;
    }
}