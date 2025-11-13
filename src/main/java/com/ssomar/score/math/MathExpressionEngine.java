package com.ssomar.score.math;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advanced Math Expression Engine for SCore
 *
 * Supports:
 * - Basic arithmetic: +, -, *, /, %
 * - Parentheses for grouping
 * - Mathematical functions: sin, cos, sqrt, abs, min, max, etc.
 * - Conditional expressions: if(condition, trueValue, falseValue)
 * - Comparison operators: ==, !=, <, >, <=, >=
 * - Logical operators: &&, ||, !
 *
 * Example usage:
 * - MathExpressionEngine.evaluate("2 + 3 * 4") → 14.0
 * - MathExpressionEngine.evaluate("sqrt(16)") → 4.0
 * - MathExpressionEngine.evaluate("if(5 > 3, 10, 20)") → 10.0
 * - MathExpressionEngine.evaluate("min(5, max(2, 8))") → 5.0
 *
 * @author SCore Math Engine
 * @version 1.0
 */
public class MathExpressionEngine {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)\\s*\\(");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+\\.?\\d*");

    /**
     * Evaluate a mathematical expression
     * @param expression The expression to evaluate
     * @return The result as a double
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static double evaluate(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }

        try {
            return evaluateExpression(expression.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression: " + expression + " - " + e.getMessage(), e);
        }
    }

    /**
     * Evaluate a mathematical expression and return as string
     * @param expression The expression to evaluate
     * @return The result as a string
     */
    public static String evaluateAsString(String expression) {
        double result = evaluate(expression);
        // Remove unnecessary decimal points for whole numbers
        if (result == (long) result) {
            return String.valueOf((long) result);
        }
        return String.valueOf(result);
    }

    /**
     * Check if a string is a valid mathematical expression
     * @param expression The expression to check
     * @return true if valid, false otherwise
     */
    public static boolean isValidExpression(String expression) {
        try {
            evaluate(expression);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static double evaluateExpression(String expr) {
        // Handle logical OR (lowest precedence)
        int orPos = findOperatorOutsideParentheses(expr, "||");
        if (orPos != -1) {
            double left = evaluateExpression(expr.substring(0, orPos));
            double right = evaluateExpression(expr.substring(orPos + 2));
            return (left != 0 || right != 0) ? 1 : 0;
        }

        // Handle logical AND
        int andPos = findOperatorOutsideParentheses(expr, "&&");
        if (andPos != -1) {
            double left = evaluateExpression(expr.substring(0, andPos));
            double right = evaluateExpression(expr.substring(andPos + 2));
            return (left != 0 && right != 0) ? 1 : 0;
        }

        // Handle comparison operators
        String[] compOps = {"==", "!=", "<=", ">=", "<", ">"};
        for (String op : compOps) {
            int pos = findOperatorOutsideParentheses(expr, op);
            if (pos != -1) {
                double left = evaluateExpression(expr.substring(0, pos));
                double right = evaluateExpression(expr.substring(pos + op.length()));
                return evaluateComparison(left, right, op) ? 1 : 0;
            }
        }

        // Handle addition and subtraction
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if ((c == '+' || c == '-') && i > 0 && !isOperator(expr.charAt(i - 1)) && getParenthesesDepth(expr, i) == 0) {
                double left = evaluateExpression(expr.substring(0, i));
                double right = evaluateExpression(expr.substring(i + 1));
                return c == '+' ? left + right : left - right;
            }
        }

        // Handle multiplication, division, and modulo
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if ((c == '*' || c == '/' || c == '%') && getParenthesesDepth(expr, i) == 0) {
                double left = evaluateExpression(expr.substring(0, i));
                double right = evaluateExpression(expr.substring(i + 1));
                if (c == '*') return left * right;
                if (c == '/') {
                    if (right == 0) throw new ArithmeticException("Division by zero");
                    return left / right;
                }
                if (c == '%') return left % right;
            }
        }

        // Handle unary minus
        if (expr.startsWith("-")) {
            return -evaluateExpression(expr.substring(1));
        }

        // Handle logical NOT
        if (expr.startsWith("!")) {
            return evaluateExpression(expr.substring(1)) == 0 ? 1 : 0;
        }

        // Handle parentheses
        if (expr.startsWith("(") && expr.endsWith(")")) {
            return evaluateExpression(expr.substring(1, expr.length() - 1));
        }

        // Handle functions
        Matcher funcMatcher = FUNCTION_PATTERN.matcher(expr);
        if (funcMatcher.find() && funcMatcher.start() == 0) {
            String funcName = funcMatcher.group(1);
            MathFunction function = MathFunction.fromName(funcName);

            if (function != null) {
                int startParen = funcName.length();
                int endParen = findMatchingParenthesis(expr, startParen);

                if (endParen == -1) {
                    throw new IllegalArgumentException("Unmatched parentheses in function: " + funcName);
                }

                String argsStr = expr.substring(startParen + 1, endParen);
                List<Double> args = parseArguments(argsStr);

                double[] argsArray = args.stream().mapToDouble(Double::doubleValue).toArray();
                return function.execute(argsArray);
            }
        }

        // Handle numbers
        Matcher numMatcher = NUMBER_PATTERN.matcher(expr);
        if (numMatcher.matches()) {
            return Double.parseDouble(expr);
        }

        throw new IllegalArgumentException("Unable to evaluate: " + expr);
    }

    private static boolean evaluateComparison(double left, double right, String operator) {
        switch (operator) {
            case "==": return Math.abs(left - right) < 1e-10;
            case "!=": return Math.abs(left - right) >= 1e-10;
            case "<": return left < right;
            case ">": return left > right;
            case "<=": return left <= right;
            case ">=": return left >= right;
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    private static List<Double> parseArguments(String argsStr) {
        List<Double> args = new ArrayList<>();
        if (argsStr.trim().isEmpty()) {
            return args;
        }

        int depth = 0;
        int start = 0;

        for (int i = 0; i < argsStr.length(); i++) {
            char c = argsStr.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (c == ',' && depth == 0) {
                args.add(evaluateExpression(argsStr.substring(start, i).trim()));
                start = i + 1;
            }
        }

        args.add(evaluateExpression(argsStr.substring(start).trim()));
        return args;
    }

    private static int findMatchingParenthesis(String expr, int openIndex) {
        int depth = 1;
        for (int i = openIndex + 1; i < expr.length(); i++) {
            if (expr.charAt(i) == '(') depth++;
            else if (expr.charAt(i) == ')') {
                depth--;
                if (depth == 0) return i;
            }
        }
        return -1;
    }

    private static int getParenthesesDepth(String expr, int index) {
        int depth = 0;
        for (int i = 0; i < index; i++) {
            if (expr.charAt(i) == '(') depth++;
            else if (expr.charAt(i) == ')') depth--;
        }
        return depth;
    }

    private static int findOperatorOutsideParentheses(String expr, String operator) {
        int depth = 0;
        for (int i = 0; i < expr.length(); i++) {
            char c = expr.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') depth--;
            else if (depth == 0 && expr.startsWith(operator, i)) {
                // Make sure it's not part of a number (e.g., -5)
                if (operator.equals("-") && i > 0 && isOperator(expr.charAt(i - 1))) {
                    continue;
                }
                return i;
            }
        }
        return -1;
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' ||
               c == '(' || c == '=' || c == '!' || c == '<' || c == '>' || c == '&' || c == '|';
    }
}
