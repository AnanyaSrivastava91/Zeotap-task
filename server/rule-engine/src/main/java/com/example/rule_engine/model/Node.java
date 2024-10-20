package com.example.rule_engine.model;


import jdk.jfr.DataAmount;
import org.springframework.data.mongodb.core.mapping.Document;

//@Document////////////
//@Data/////////////
//@NoArgsConstructor
//@AllArgsConstructor
public class Node {
    String type;  // "operator" or "operand"
    String value; // For operator nodes, it's "AND"/"OR"; for operand nodes, it's the condition

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    Node left;    // Left child
    Node right;   // Right child

    // Constructor for operator nodes
    public Node(String type, String value, Node left, Node right) {
        this.type = type;
        this.value = value;
        this.left = left;
        this.right = right;
    }

    // Constructor for operand (leaf) nodes
    public Node(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

