package com.example.rule_engine.utils;

import com.example.rule_engine.model.Node;


public class RuleUtils {
//    public static void main(String[] args) {
//        // Given rule string
//        //String rule = "((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)";
//        String rule = "((age > 30 AND department = 'Sales') OR ((age < 25 AND department1 = 'Marketing1') OR (age < 25 AND department2 = 'Marketing2'))) AND (salary > 50000 OR experience > 5)";
//
//        // Parse the rule and build the AST
//        Node root = parseExpression(rule, false);
//
//        // Print the resulting tree structure
//        printTree(root);
//    }

    // Method to parse an expression and return the root node of the AST
    public static Node parseExpression(String expression, boolean flag) {
        expression = expression.trim();

        // Remove outer parentheses if they enclose the entire expression
        if (flag && expression.startsWith("(") && expression.endsWith(")")) {
            expression = expression.substring(1, expression.length() - 1).trim();
        }

        // Find the main operator (AND or OR) at the outer level
        int operatorIndex = findMainOperator(expression);

        if (operatorIndex == -1) {
            // No operator found, so it's a leaf node (condition)
            return new Node("operand", expression);
        }

        // Split the expression into left and right parts
        String operator = expression.substring(operatorIndex, operatorIndex + 3).trim(); // AND or OR
        String leftExpr = expression.substring(0, operatorIndex).trim();
        String rightExpr = expression.substring(operatorIndex + 3).trim();

        // Recursively build the left and right subtrees
        Node leftNode = parseExpression(leftExpr, true);
        Node rightNode = parseExpression(rightExpr, true);

        // Create an operator node
        return new Node("operator", operator, leftNode, rightNode);
    }

    // Method to find the main operator (AND or OR) in the expression
    private static int findMainOperator(String expression) {
        int level = 0; // Parentheses level tracking

        // Traverse the expression to find the top-level operator
        for (int i = 0; i < expression.length() - 2; i++) {
            char c = expression.charAt(i);

            // Manage parentheses level
            if (c == '(') {
                level++;
            } else if (c == ')') {
                level--;
            }

            // Check for AND or OR at level 0 (not inside parentheses)
            if (level == 0 && (expression.startsWith("AND", i) || expression.startsWith("OR", i))) {
                return i;
            }
        }

        // No operator found at the top level
        return -1;
    }

    // A simple method to print the tree in pre-order
    public static void printTree(Node node) {
        if (node == null) return;

        System.out.println(node.getType() + ": " + node.getValue());
        printTree(node.getLeft());
        printTree(node.getRight());
    }
}
