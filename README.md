# ARCA - Auth Service

## Overview
The **ARCA Auth Service** is a microservice responsible for managing authentication and authorization within the ARCA ecosystem. It provides endpoints for user registration, login, token generation, and user management with support for roles and permissions.

This service is built using **Java 21**, **Spring Boot 3.3.1**, and integrates with **MySQL** as its primary database. Additionally, it utilizes **JWT** for secure token-based authentication and provides Swagger-based API documentation.

---

## Features
- **User Authentication**: Register and log in users.
- **Role-based Authorization**: Control access to resources based on roles.
- **Token Management**: Secure JWT token issuance and validation.
- **API Documentation**: Accessible via Swagger UI.
- **Database Versioning**: Managed using Flyway.
- **Extensibility**: Modular design for scalability.

---

## Directory Structure
```plaintext
lucasfmerino-arca-auth-service/
├── README.md
├── mvnw
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── arca/
│   │   │           └── auth_service/
│   │   │               ├── AuthServiceApplication.java
│   │   │               ├── advice/        # Exception handling
│   │   │               ├── infra/         # Documentation and Security Config
│   │   │               └── modules/       # Core modules (auth, user, role)
│   │   └── resources/                      # Properties and migrations
│   └── test/                               # Test files
└── .mvn/                                   # Maven wrapper
```

---

## Technology Stack
- **Java**: Version 21.
- **Spring Boot**: 3.3.1
- **Database**: MySQL (with H2 for testing).
- **Authentication**: JWT-based.
- **Documentation**: SpringDoc OpenAPI.
- **Dependency Management**: Maven.

---

## Getting Started

### Prerequisites
- **Java 21**
- **Maven** (or use `mvnw`/`mvnw.cmd` provided).
- **MySQL** (or Docker for containerized database).

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/lucasfmerino/arca-auth-service.git
   cd arca-auth-service
   ```

2. Configure the database:
    - Update database properties in `src/main/resources/application-dev.properties` if needed.
    - Alternatively, use Docker to spin up a database:
      ```bash
      docker container run --name arca-auth-db -p 3308:3306 -e MYSQL_ROOT_PASSWORD=your_password -e MYSQL_DATABASE=arca_auth -d mysql
      ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Access Swagger UI for API documentation:
    - Development: `http://localhost:<dynamic-port>/swagger-ui.html`
    - Production: URL depends on deployment.

---

## API Endpoints

### Public Endpoints
- **User Registration**:
  ```http
  POST /auth/register
  ```
- **Login**:
  ```http
  POST /auth/login
  ```

### Protected Endpoints
- **Get User by ID**:
  ```http
  GET /api/users/{id}
  ```
- **Update User**:
  ```http
  PUT /api/users
  ```
- **Delete User**:
  ```http
  DELETE /api/users/{id}
  ```

---

## Environment Configuration
Environment-specific configurations can be found in the following files:
- **Development**: `application-dev.properties`
- **Production**: `application-prd.properties`
- **Testing**: `application-test.properties`

Set the active profile using:
```properties
spring.profiles.active=dev
```

---

## Database

### Schema
The database schema is managed by Flyway migrations. Initial migration is located at:
```plaintext
src/main/resources/db/migration/V1__create_table_users.sql
```

### Running Migrations
Flyway automatically applies migrations on startup. Ensure the database URL and credentials are correctly set in the properties file.

---

## Testing
- Unit tests are located under `src/test/java`.
- Run tests using:
  ```bash
  ./mvnw test
  ```

---

## Deployment
1. Build the application:
   ```bash
   ./mvnw clean package
   ```
2. Deploy the generated JAR file:
   ```bash
   java -jar target/app-auth.jar
   ```
3. Configure environment variables for production as specified in `application-prd.properties`.

---

## License
This project is licensed under the Apache 2.0 License. See `LICENSE` for details.

---

## Contact
For inquiries or support, reach out at:
- **Email**: [hivetron.dev@gmail.com](mailto:hivetron.dev@gmail.com)