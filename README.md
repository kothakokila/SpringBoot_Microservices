# Spring Boot Microservices Project

Spring Boot microservices application that includes multiple services and is configured with an API Gateway and Netflix Eureka for service discovery.

## Project Structure

The project is divided into three microservices:

1. **Order Service**: Manages order-related operations.
2. **Inventory Service**: Handles inventory management.
3. **Product Service**: Manages product-related operations.

### Additional Components

- **API Gateway**: Routes incoming HTTP requests to the appropriate microservice based on the request.
- **Discovery Service**: Configured using Netflix Eureka for service registration and discovery.

## Technologies Used

- **Spring Boot**: Framework for creating microservices.
- **Spring Cloud Netflix Eureka**: Service registration and discovery.
- **Spring Cloud Gateway**: API Gateway for routing requests.
- **Spring Data JPA**: For database interactions.
- **H2 Database**: In-memory database for development and testing.
- **MySQL**: Production database.

## Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher
- MySQL (for production)
