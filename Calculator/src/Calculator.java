import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calculator {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        List<Object> history = new ArrayList<>();
        System.out.print("Enter first number: ");
        int firstNumber = scanner.nextInt();
        System.out.print("Enter second number: ");
        int secondNumber = scanner.nextInt();
        System.out.print("Enter operation: ");
        char operation = scanner.next().charAt(0);
        int result = 0;
        try {
            result = calculate(firstNumber, secondNumber, operation);
        } catch (ArithmeticException exception) {
            System.out.println(exception.getMessage());
        }
        while (true) {
            System.out.print("Do you want to calculate another number [Y/N]: ");
            char response = scanner.next().charAt(0);
            if (response == 'N' || response == 'n') break;
            System.out.print("Enter operation: ");
            operation = scanner.next().charAt(0);
            System.out.print("Enter another number: ");
            int anotherNumber = scanner.nextInt();
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
                answer = firstNumber + secondNumber;
                break;
            case '-':
                answer = firstNumber - secondNumber;
                break;
            case '*':
                answer = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber == 0) throw new ArithmeticException("Cannot divide by zero");
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }
        return answer;
    }

}