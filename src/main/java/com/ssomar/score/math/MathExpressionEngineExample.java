package com.ssomar.score.math;

/**
 * Example usage of the Math Expression Engine
 * This class demonstrates various capabilities of the engine
 *
 * @author SCore Math Engine
 * @version 1.0
 */
public class MathExpressionEngineExample {

    /**
     * Run examples demonstrating the Math Expression Engine capabilities
     * This method is for demonstration purposes only
     */
    public static void runExamples() {
        System.out.println("=== Math Expression Engine Examples ===\n");

        // Basic arithmetic
        System.out.println("Basic Arithmetic:");
        printEval("2 + 3");
        printEval("10 - 4");
        printEval("3 * 4");
        printEval("20 / 5");
        printEval("10 % 3");
        printEval("2 + 3 * 4");  // Order of operations
        printEval("(2 + 3) * 4");  // Parentheses

        // Mathematical functions
        System.out.println("\nMathematical Functions:");
        printEval("abs(-5)");
        printEval("min(10, 20)");
        printEval("max(10, 20)");
        printEval("round(3.7)");
        printEval("floor(3.7)");
        printEval("ceil(3.2)");
        printEval("sqrt(16)");
        printEval("pow(2, 3)");

        // Trigonometric functions
        System.out.println("\nTrigonometric Functions:");
        printEval("sin(0)");
        printEval("cos(0)");
        printEval("tan(0)");

        // Comparison operators
        System.out.println("\nComparison Operators:");
        printEval("5 > 3");
        printEval("2 == 2");
        printEval("10 < 5");
        printEval("7 >= 7");
        printEval("3 != 3");

        // Logical operators
        System.out.println("\nLogical Operators:");
        printEval("1 && 1");
        printEval("1 && 0");
        printEval("1 || 0");
        printEval("!0");
        printEval("!(5 > 10)");

        // Conditional expressions
        System.out.println("\nConditional Expressions:");
        printEval("if(5 > 3, 10, 20)");
        printEval("if(2 == 3, 100, 200)");
        printEval("if(1, 999, 0)");

        // Nested expressions
        System.out.println("\nNested Expressions:");
        printEval("min(5, max(2, 8))");
        printEval("sqrt(pow(3, 2) + pow(4, 2))");  // Pythagorean theorem
        printEval("if(sqrt(16) == 4, 1, 0)");
        printEval("round(sqrt(50))");

        // Complex practical examples
        System.out.println("\nComplex Examples:");
        printEval("max(1, 10 - floor(5.7))");  // Distance-based scaling
        printEval("round(10 * 1.5 + 10)");  // Level-based reward
        printEval("(10 > 5) && (15 >= 10)");  // Multiple conditions
        printEval("min(100, max(10, 50))");  // Clamping values

        System.out.println("\n=== All examples completed successfully! ===");
    }

    /**
     * Helper method to evaluate and print an expression
     */
    private static void printEval(String expression) {
        try {
            String result = MathExpressionEngine.evaluateAsString(expression);
            System.out.println("  " + expression + " = " + result);
        } catch (Exception e) {
            System.out.println("  " + expression + " = ERROR: " + e.getMessage());
        }
    }

    /**
     * Main method for standalone testing
     * @param args Command line arguments (unused)
     */
    public static void main(String[] args) {
        runExamples();
    }
}
