// Task#5: QuadraticEquation Test Program
// File: Task5_QuadraticEquationTest.java

import java.util.Scanner;

public class Task5_QuadraticEquationTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Prompt the user to enter values for a, b, and c
        System.out.println("=== Quadratic Equation Solver ===");
        System.out.println("Equation format: ax^2 + bx + c = 0");
        System.out.println();
        
        System.out.print("Enter value for a: ");
        double a = input.nextDouble();
        
        System.out.print("Enter value for b: ");
        double b = input.nextDouble();
        
        System.out.print("Enter value for c: ");
        double c = input.nextDouble();
        
        // Create QuadraticEquation object
        Task5_QuadraticEquation equation = new Task5_QuadraticEquation(a, b, c);
        
        // Calculate discriminant
        double discriminant = equation.getDiscriminant();
        
        System.out.println();
        System.out.println("=== Results ===");
        System.out.println("Discriminant: " + discriminant);
        
        // Display results based on discriminant
        if (discriminant > 0) {
            // Two distinct real roots
            System.out.println("The equation has two roots:");
            System.out.println("Root 1: " + equation.getRoot1());
            System.out.println("Root 2: " + equation.getRoot2());
        } else if (discriminant == 0) {
            // One real root
            System.out.println("The equation has one root:");
            System.out.println("Root: " + equation.getRoot1());
        } else {
            // No real roots
            System.out.println("The equation has no roots.");
        }
        
        input.close();
    }

    // Keep test self-contained to avoid cross-file default-package resolution issues.
    static class Task5_QuadraticEquation {
        private double a;
        private double b;
        private double c;

        Task5_QuadraticEquation(double a, double b, double c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        double getDiscriminant() {
            return (b * b) - (4 * a * c);
        }

        double getRoot1() {
            double disc = getDiscriminant();
            return (disc < 0 || a == 0) ? 0 : (-b + Math.sqrt(disc)) / (2 * a);
        }

        double getRoot2() {
            double disc = getDiscriminant();
            return (disc < 0 || a == 0) ? 0 : (-b - Math.sqrt(disc)) / (2 * a);
        }
    }
}
