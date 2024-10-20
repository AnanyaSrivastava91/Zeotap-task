package com.example.rule_engine.service;

import com.example.rule_engine.model.Rule;
import com.example.rule_engine.model.Node;
import com.example.rule_engine.repository.RuleRepository;
import com.example.rule_engine.utils.RuleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    // Save individual rule to the database
    public Rule saveRule(Rule rule) {
        return ruleRepository.save(rule);
    }

    // Retrieve all rules from the database
    public List<Rule> getAllRules() {
        return ruleRepository.findAll();
    }

    // Create and save a rule from a rule string
    public Rule createRuleFromString(String ruleString) {
        Node rootNode = RuleUtils.parseExpression(ruleString, false);

        // Create Rule object using the rootNode
        Rule rule = new Rule();
        rule.setRoot(rootNode); // Set the root node of the AST

        // Save the Rule object and return it
        return ruleRepository.save(rule);
    }

    // Combine multiple rules into a single AST
    public Node combineRules(List<String> ruleStrings) {
        if (ruleStrings == null || ruleStrings.isEmpty()) {
            throw new IllegalArgumentException("Rule strings cannot be null or empty");
        }

        Node combinedAST = null;

        // Loop through each rule string and combine them into a single AST
        for (String ruleString : ruleStrings) {
            // Parse each rule string into an AST
            Node currentAST = RuleUtils.parseExpression(ruleString, false);

            if (combinedAST == null) {
                // First rule initializes the combined AST
                combinedAST = currentAST;
            } else {
                // Combine the current AST with the existing combined AST using 'AND' operator
                combinedAST = new Node(
                        "operator", // Node type
                        "AND",      // Logical operator to combine rules
                        combinedAST, // Left subtree: the existing AST
                        currentAST   // Right subtree: the new rule's AST
                );
            }
        }

        return combinedAST;
    }

    // Evaluate the AST rule against user data (JSON-like dictionary)
    public boolean evaluateRule(Node node, Map<String, Object> userData) {
        if (node == null) {
            return false;
        }

        // If the node is an operand, evaluate the condition
        if (node.getType().equals("operand")) {
            return evaluateCondition(node.getValue(), userData);
        }

        // If the node is an operator, recursively evaluate its children
        boolean leftResult = evaluateRule(node.getLeft(), userData);
        boolean rightResult = evaluateRule(node.getRight(), userData);

        // Apply the logical operator
        switch (node.getValue().toUpperCase()) {
            case "AND":
                return leftResult && rightResult;
            case "OR":
                return leftResult || rightResult;
            default:
                throw new IllegalArgumentException("Unknown operator: " + node.getValue());
        }
    }

    // Evaluate an individual condition (e.g., "age > 30", "department = 'Sales'")
    private boolean evaluateCondition(String condition, Map<String, Object> userData) {
        String[] parts;

        if (condition.contains(">=")) {
            parts = condition.split(">=");
            String field = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            return Integer.parseInt(userData.get(field).toString()) >= value;

        } else if (condition.contains("<=")) {
            parts = condition.split("<=");
            String field = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            return Integer.parseInt(userData.get(field).toString()) <= value;

        } else if (condition.contains(">")) {
            parts = condition.split(">");
            String field = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            return Integer.parseInt(userData.get(field).toString()) > value;

        } else if (condition.contains("<")) {
            parts = condition.split("<");
            String field = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());
            return Integer.parseInt(userData.get(field).toString()) < value;

        } else if (condition.contains("=")) {
            parts = condition.split("=");
            String field = parts[0].trim();
            String value = parts[1].trim().replace("'", ""); // Remove any surrounding quotes
            return userData.get(field).toString().equals(value);

        } else {
            throw new IllegalArgumentException("Unknown condition: " + condition);
        }
    }
}
