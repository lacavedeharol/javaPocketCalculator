package com.lacavedeharol.calculator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class CalculatorModel {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("((?<=[-+*/])|(?=[-+*/]))");

    public static String calculate(String inputOperation) {
        try {
            String postfix = infixToPostfix(inputOperation);
            double result = evaluatePostfix(postfix);

            if (result == (long) result) {
                return String.format("%d", (long) result);
            } else {
                return String.format("%s", result);
            }
        } catch (Exception e) {
            return "Error";
        }
    }

    private static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<String> stack = new Stack<>();

        String[] tokens = TOKEN_PATTERN.split(infix);
        List<String> processedTokens = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.equals("-")) {

                boolean isFirstToken = (i == 0);
                boolean isAfterOperator = (i > 0 && isOperator(tokens[i - 1]));

                if (isFirstToken || isAfterOperator) {
                    processedTokens.add("0");
                }
            }
            processedTokens.add(token);
        }

        for (String token : processedTokens) {
            if (isNumeric(token)) {
                postfix.append(token).append(" ");
            } else if (isOperator(token)) {
                while (!stack.isEmpty() && hasHigherOrEqualPrecedence(stack.peek(), token)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(" ");
        }
        return postfix.toString().trim();
    }

    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(postfix);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (isNumeric(token)) {
                stack.push(Double.valueOf(token));
            } else if (isOperator(token)) {
                double b = stack.pop();
                double a = stack.pop();
                stack.push(applyOperator(a, b, token));
            }
        }
        return stack.pop();
    }

    private static int getPrecedence(String operator) {
        return switch (operator) {
            case "+", "-" ->
                1;
            case "*", "/" ->
                2;
            default ->
                -1;
        };
    }

    private static boolean hasHigherOrEqualPrecedence(String op1, String op2) {
        return getPrecedence(op1) >= getPrecedence(op2);
    }

    private static boolean isOperator(String token) {
        return "+-*/".contains(token) && token.length() == 1;
    }

    private static boolean isNumeric(String str) {
        try {
            Double.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static double applyOperator(double a, double b, String operator) {
        switch (operator) {
            case "+" -> {
                return a + b;
            }
            case "-" -> {
                return a - b;
            }
            case "*" -> {
                return a * b;
            }
            case "/" -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                return a / b;
            }
            default ->
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
