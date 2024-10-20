Rule Engine - Zeotap Task

This project implements a 3-tier rule engine designed to dynamically evaluate user eligibility based on various attributes such as age, department, income, and spend. The rule engine uses Abstract Syntax Trees (ASTs) to represent rules and perform logical operations to determine user eligibility.


Features:

Rule Creation: Create rules using strings like age > 30 AND department = 'Sales'.
AST Representation: Automatically converts rule strings into ASTs.
Rule Combination: Combines multiple rules into a single AST using logical operators.
Evaluation: Evaluates a rule AST against user data.
MongoDB Integration: Stores rules and retrieves them for reuse.
REST APIs: APIs for rule management and evaluation.


Tech Stack:

Backend: Spring Boot
Database: MongoDB
Frontend: (Optional for extension)
Language: Java
Tools: Maven


Folder Structure:

.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.rule_engine
│   │   │       ├── controller
│   │   │       │   └── RuleController.java  // Exposes APIs for rule management
│   │   │       ├── dto
│   │   │       │   └── RuleEngineDto.java   // DTO for rule engine
│   │   │       ├── model
│   │   │       │   └── Node.java            // Node class for AST
│   │   │       │   └── Rule.java            // Rule class for MongoDB storage
│   │   │       ├── repository
│   │   │       │   └── RuleRepository.java  // MongoDB repository
│   │   │       ├── service
│   │   │       │   └── RuleService.java     // Business logic for rule engine
│   │   │       └── utils
│   │   │           └── RuleUtils.java       // Utility functions for rule parsing
│   │   └── resources
│   │       └── application.properties       // MongoDB connection and application configuration
├── pom.xml                                  // Maven dependencies
└── README.md                                // Project documentation


MongoDB Configuration
Ensure you have MongoDB running and configured properly.
Update your application.properties file with the correct MongoDB URI:

spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster0.mongodb.net/<dbname>?retryWrites=true&w=majority


APIs:
Create Rule
POST /api/rules/create
Request Body:

{
  "ruleString": "age > 30 AND department = 'Sales'"
}
Response: Returns the created rule.

Get All Rules
GET /api/rules/all
Response: Returns all saved rules.

Combine Rules
POST /api/rules/combine
Request Body:

[
  "age > 30 AND department = 'Sales'",
  "income > 50000"
]

Response: Returns the combined AST.

Evaluate Rule
POST /api/rules/evaluate
Request Body:

{
  "ruleString": "age > 30 AND department = 'Sales'",
  "data": {
    "age": 35,
    "department": "Sales"
  }
}
Response: Returns true if the rule passes, false otherwise.


Running the Project

Clone the repository:

//bash
Copy code
git clone https://github.com/AnanyaSrivastava91/Zeotap-task.git

Navigate to the project directory:

//bash
Copy code
cd Zeotap-task
Build and run the project using Maven:

//arduino
Copy code
mvn spring-boot:run
Access the APIs using Postman or curl on http://localhost:8080.

Contributing:
Feel free to submit issues or create pull requests if you'd like to improve the project!



