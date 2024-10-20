package com.example.rule_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoRepositories(basePackages = "com.example.rule_engine.repository")
public class RuleEngineApplication {

	public static void main(String[] args) {

		SpringApplication.run(RuleEngineApplication.class, args);
	}

}
