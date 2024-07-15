# Product Management API

## Overview
The Product Management API is a Spring Boot application that allows users to manage product data. It provides a RESTful interface for CRUD operations on products.
The application includes user authentication and authorization, data validation, and error handling.

## Table of Contents
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Project Structure](#project-structure)
- [Endpoints](#endpoints)
- [Security Configuration](#security-configuration)
- [Error Handling](#error-handling)

## Getting Started
### Prerequisites
- Java 17 or higher
- Maven 3.8.1 or higher
- Spring Boot 3.0 or higher

### Installation
- Clone the repo
- Build project using maven
- Run the project using maven (mvn spring-boot:run)
- Also, you can import project in IDE, build and then run from ProductManagementApiApplication class

## Configuration
### application.properties
- server.port - Configures the port the application runs on.
- spring.datasource.url - URL for postgresql database with default port
- spring.datasource.username: and spring.datasource.password: - Update this fields with your own info used when creating the database in postgresql
- spring.jpa.hibernate.ddl-auto=update - It instructs Hibernate to update the existing database schema to match the structure defined by your JPA entity classes.

## Project Structure
- **ProductManagementApiApplication**: The main entry point of the application.
- **Controller**: Handles HTTP requests and responses.
    - **ProductController**: Manages CRUD operations for products.
- **Service**: Contains business logic.
    - **ProductService**: Provides methods to interact with the product repository.
- **Repository**: Interfaces with the database.
    - **ProductRepository**: JpaRepository for Product entities.
- **Model**: Defines the data model for the application.
    - **Product**: Entity representing a product with fields such as id, name, price, quantity and brand.
- **DTO**: Defines the data transfer objects for the application.
    - **ProductDTO**: Represents a product with fields such as id, name, price, quantity and brand.
    - **ProductPriceUpdateRequest**: Represents a product for updating 1 field (price) 
- **SecurityConfig**: Configures security settings including user roles and permissions.
- **GlobalExceptionHandler**: Handles application-wide exceptions and provides meaningful error responses.

## Endpoints
### ProductController
- **GET /api/v1/products**: Fetch all products.
- **GET /api/v1/products/{id}**: Fetch a product by ID.
- **POST /api/v1/products**: Insert a new product.
- **PUT /api/v1/products/{id}**: Update an existing product.
- **PATCH /api/v1/products/{id}/price**: Update only the product price
- **DELETE /api/v1/products/{id}**: Delete a product by ID.

## Security Configuration
### SecurityConfig
- The application is configured with in-memory authentication for development and testing purposes. You can set up the users by modifying the SecurityConfig class.
- Uses HTTP Basic authentication.
- **Roles**:
    - **USER**: Can read product information.
    - **ADMIN**: Can read, create, update and delete product information.
- Replace userPass and adminPass with strong passwords before deploying the application to a production environment.
- Note: Ensure to replace the default credentials and configure an external authentication provider for production use to enhance security.

## Error Handling
### GlobalExceptionHandler
Handles various exceptions and returns appropriate HTTP statuses and messages:
- **404 Not Found**:
    - Product not found.
- **409 Conflict**:
    - Attempt to create a product with a pre-existing ID.
    - Attempt to update a product with its ID different from the one passed as path variable.
- **400 Bad Request**:
    - Request body data fails validation (e.g., missing or wrong required fields).
- **500 Internal Server Error**:
    - Unexpected database access error or other exceptions.


