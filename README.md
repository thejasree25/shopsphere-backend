# 🛒 ShopSphere — Backend

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.1.2-6DB33F?logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Spring_Security-Authentication-6DB33F?logo=springsecurity" alt="Spring Security" />
  <img src="https://img.shields.io/badge/PostgreSQL-Database-4169E1?logo=postgresql" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven" alt="Maven" />
</p>

## 📌 About the Project

**ShopSphere** is the backend application for a full-stack e-commerce platform. It is built using Java and Spring Boot and provides server-side functionality for the frontend.

The backend is responsible for processing API requests, applying business rules, communicating with the PostgreSQL database, and handling authentication and security.

**Backend repository:**  
https://github.com/thejasree25/shopsphere-backend

**Frontend repository:**  
https://github.com/thejasree25/shopsphere-frontend

## ✨ Main Responsibilities

- Provide REST APIs for the frontend.
- Process product-related requests.
- Handle authentication and protected resources.
- Communicate with PostgreSQL using JPA.
- Validate incoming request data.
- Support shopping and order workflows implemented in the application.
- Integrate payment-related functionality using Razorpay where configured.

## 🛠️ Technology Stack and Why It Is Used

| Technology | Why it is used |
|---|---|
| Java 21 | Implements the backend logic using an object-oriented programming language. |
| Spring Boot | Simplifies application configuration and backend development. |
| Spring Web MVC | Creates REST endpoints and handles HTTP requests and responses. |
| Spring Data JPA | Simplifies database operations through repository interfaces. |
| Hibernate | Implements ORM functionality to map Java entities to database tables. |
| PostgreSQL | Stores application data in a relational database. |
| Spring Security | Provides authentication and access-control functionality. |
| JWT | Supports token-based authentication using the configured JWT implementation. |
| Bean Validation | Validates request data and helps reject invalid input. |
| Razorpay Java SDK | Supports integration with the Razorpay payment service. |
| Maven | Manages dependencies and builds the Java application. |
| Lombok | Reduces boilerplate Java code where its annotations are used. |
| Postman | Can be used to test REST API requests and responses. |
| Git and GitHub | Track changes and host the source code. |

## 🔄 Backend Workflow

```text
React Frontend
      ↓
HTTP Request
      ↓
Spring Security
      ↓
REST Controller
      ↓
Business Logic
      ↓
Repository
      ↓
Spring Data JPA / Hibernate
      ↓
PostgreSQL Database
      ↓
Response returned to frontend
```

For a database operation, the controller receives a request, the application processes it, and the repository performs the required database operation. The backend then returns an HTTP response to the frontend.

## 📁 Project Structure

The repository uses a Maven-based Spring Boot project. The following illustrates the common roles of its files; check `src/main/java` for the exact package and class names.

```text
shopsphere-backend/
├── .mvn/
│   └── wrapper/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/shopsphere/
│   │   │       ├── controller/    # REST endpoints
│   │   │       ├── service/       # Business logic
│   │   │       ├── repository/    # Database operations
│   │   │       ├── entity/        # Database entity classes
│   │   │       ├── config/        # Application configuration
│   │   │       └── security/      # Security-related classes
│   │   └── resources/
│   │       └── application.properties
│   └── test/                      # Automated tests
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

*The package names above are illustrative. Keep the structure description aligned with the actual source files in your repository.*

## ⚙️ Installation and Setup

### Prerequisites

Install the following:

- JDK 21
- PostgreSQL
- Git
- A terminal or IDE such as IntelliJ IDEA or VS Code

### Step 1: Clone the repository

```bash
git clone https://github.com/thejasree25/shopsphere-backend.git
cd shopsphere-backend
```

### Step 2: Create the PostgreSQL database

Open PostgreSQL or pgAdmin and create a database for the application.

```sql
CREATE DATABASE shopsphere;
```

If your existing `application.properties` specifies a different database name, use that name instead.

### Step 3: Configure `application.properties`

Open:

```text
src/main/resources/application.properties
```

Configure the database connection using your local PostgreSQL settings. For example:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/shopsphere}
spring.datasource.username=${DB_USERNAME:postgres}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Set `DB_PASSWORD` in your environment before starting the application. Keep the configuration consistent with your existing entity mappings and application settings.

**Security note:** Do not commit database passwords, JWT signing secrets, or payment API keys to GitHub. Keep sensitive values in environment variables or an untracked local configuration file.

### Step 4: Build the application

**Windows:**

```bash
mvnw.cmd clean install
```

**Linux or macOS:**

```bash
./mvnw clean install
```

### Step 5: Run the backend

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

**Linux or macOS:**

```bash
./mvnw spring-boot:run
```

The backend will use the port configured in the application, commonly:

```text
http://localhost:8080
```

Use the actual API endpoints defined in the controllers when testing the application.

## 🔐 Authentication and Security

The project includes Spring Security and JWT-related dependencies.

- Spring Security handles security rules and access control.
- JWT supports token-based authentication when configured by the application.
- Protected endpoints require the appropriate authentication and permissions.
- Sensitive configuration values should be stored outside the public repository.

A JWT-based flow generally works as follows:

```text
User submits login details
        ↓
Backend validates credentials
        ↓
Backend issues a token
        ↓
Frontend stores the token as configured
        ↓
Frontend sends the token with protected requests
        ↓
Backend validates the token
        ↓
Request is allowed or rejected
```

The exact login endpoints, token format, and authorization rules depend on the security configuration in the source code.

## 🗄️ Database Workflow

1. The controller receives a request.
2. The service layer applies business rules, if present.
3. The repository communicates with the database through JPA.
4. Hibernate maps between Java objects and database records.
5. The backend returns the result to the frontend.

PostgreSQL provides persistent storage, so application data can remain available after the server restarts.

## 🧪 Testing the Backend

You can use Postman to test the REST APIs.

1. Start the backend.
2. Select the HTTP method required by the endpoint.
3. Enter the URL of the endpoint defined in the controller.
4. Provide the required headers and JSON request body.
5. Send the request and verify the status code and response.

For endpoints that require authentication, include a valid token in the format expected by the security configuration.

## 🎯 Interview Questions and Answers

### 1. Explain your ShopSphere backend project.

ShopSphere is the backend of my e-commerce application. I used Java and Spring Boot to create server-side functionality, Spring Data JPA to interact with PostgreSQL, and Spring Security with JWT-related functionality for authentication and access control.

### 2. Why did you use Spring Boot?

Spring Boot simplifies Java backend development through auto-configuration, dependency management, and an embedded server. It helps me build and run REST APIs with less manual configuration.

### 3. What is REST API?

A REST API allows applications to communicate over HTTP. For example, a frontend can send a GET request to retrieve data or a POST request to submit new data.

### 4. What is the difference between JPA and Hibernate?

JPA defines the standard for object-relational mapping in Java. Hibernate is an implementation of that standard and handles much of the object-to-database mapping.

### 5. Why did you use PostgreSQL?

PostgreSQL is a relational database that supports structured data, relationships, constraints, and SQL queries. It is suitable for storing related e-commerce data.

### 6. What is Spring Security?

Spring Security provides authentication and authorization features. It helps determine who can access an application and which resources they are permitted to use.

### 7. What is JWT authentication?

JWT authentication uses a signed token to represent authentication information. The client sends the token with requests, and the backend validates it according to its security configuration.

### 8. What is the purpose of a repository?

A repository provides methods for accessing and modifying database records. Spring Data JPA can generate common database operations without requiring every query to be written manually.

### 9. What is the purpose of Maven?

Maven manages project dependencies, compiles the code, runs tests, and packages the application.

### 10. How does the frontend connect to the backend?

The React frontend sends HTTP requests to backend REST endpoints. Spring Boot processes the requests, accesses PostgreSQL when required, and returns responses that the frontend can display.

## 🚀 Future Improvements

- Add comprehensive unit and integration tests.
- Document APIs using OpenAPI/Swagger if configured.
- Improve centralized exception handling and logging.
- Add deployment configuration and production monitoring.
- Strengthen validation and security checks.

## 👩‍💻 Author

**Thejasree Ganginepalli**

GitHub: https://github.com/thejasree25

