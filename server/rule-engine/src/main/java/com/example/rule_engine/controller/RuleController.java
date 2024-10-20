package com.example.rule_engine.controller;

import com.example.rule_engine.model.Rule;
import com.example.rule_engine.model.Node;
import com.example.rule_engine.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rules")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    // API to create a rule from a rule string
    @PostMapping("/create")
    public ResponseEntity<Rule> createRule(@RequestBody Map<String, String> ruleRequest) {
        String ruleString = ruleRequest.get("ruleString");
        Rule createdRule = ruleService.createRuleFromString(ruleString);
        return ResponseEntity.ok(createdRule);
    }

    // API to retrieve all rules
    @GetMapping("/all")
    public ResponseEntity<List<Rule>> getAllRules() {
        List<Rule> rules = ruleService.getAllRules();
        return ResponseEntity.ok(rules);
    }

    // API to combine multiple rules
    @PostMapping("/combine")
    public ResponseEntity<Node> combineRules(@RequestBody List<String> ruleStrings) {
        Node combinedAST = ruleService.combineRules(ruleStrings);
        return ResponseEntity.ok(combinedAST);
    }

    // API to evaluate rules based on provided data
    @PostMapping("/evaluate")
    public ResponseEntity<Boolean> evaluateRule(@RequestBody Map<String, Object> evaluationRequest) {
        Rule ruleAST = ruleService.createRuleFromString((String) evaluationRequest.get("ruleString"));
        Map<String, Object> data = (Map<String, Object>) evaluationRequest.get("data");

        boolean result = ruleService.evaluateRule(ruleAST.getRoot(), data);
        return ResponseEntity.ok(result);
    }
}
