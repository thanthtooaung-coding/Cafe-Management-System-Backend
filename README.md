# Cafe Management System
Cafe Management System - Spring Boot

## Description
A Spring Boot application for managing cafe operations, including staff management, menu management, and order processing. 

## Features
- Manage employees and their roles
- Track inventory and menu items
- Process customer orders
- Generate reports

## Pre-requisites
Before running the project, ensure you have the following installed:

- Java Development Kit (JDK)
	- JDK 21
- Integrated Development Environment (IDE)
	- Eclipse or IntelliJ IDEA or VS Code
- Build Tool
	- Apache Maven (Version 3.x)
- Database
	- PostgreSQL (Version 16)
- Version Control System
  - Git (Latest stable version)
    
## How to Run
1. Clone the repository:

```
git clone https://github.com/thanthtooaung-coding/Cafe-Management-System-Backend
```

2. Navigate to the project directory.

```
cd Cafe-Management-System-Backend
```

3. Build the project using Maven.

```
mvn clean install
```

4. Run the application.

```
mvn spring-boot:run
```
	
5. Access the application through a web browser at.

```
http://localhost:8080/
```

## Swagger with Spring OpenAPI
This project utilizes Swagger to provide interactive API documentation. Follow these steps to access the API documentation:

1. After running the application, navigate to `http://localhost:8080/swagger-ui.html` in your web browser.
2. This will open the Swagger UI, where you can explore the available API end points, parameters, request bodies, and responses.
3. Use the Swagger UI interface to interact with the API, including testing end points directly from the browser.

## Coding Standards
### Java Class Format
- Use CamelCase for class names (e.g., `EmployeeService`, `ProductController`).
- Class names should be nouns and should represent a single responsibility.
- Class files should be named after the class they contain, with the file extension `.java` (e.g., `EmployeeService.java`).

### Method Format
- Use camelCase for method names (e.g., `getEmployees()`, `calculateProgress()`).
- Method names should be verbs or verb phrases, indicating actions performed by the method.
- Methods should have clear and concise purposes, adhering to the Single Responsibility Principle.
- Methods should be properly documented using Java-doc comments to explain their purpose, parameters, and return values.

### General Coding Standards
- Follow the Java coding conventions outlined in the Oracle Java SE Coding Conventions.
- Use meaningful variable names that reflect their purpose.
- Limit the length of lines to 80-120 characters to ensure readability.
- Write modular and reusable code to promote maintainability.
- Utilize appropriate design patterns and best practices where applicable.
