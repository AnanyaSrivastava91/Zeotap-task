package com.example.rule_engine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rule_engine")
public class Rule {
    @Id
    private String id;
    private Node root;  // Root of the AST

    // Constructors
    public Rule() {}

    public Rule(Node root) {
        this.root = root;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
