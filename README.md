

# Ecommerce Jackie Shop

## Overview
`ecommerce-shop` is a Spring Boot application designed to manage an e-commerce platform. It includes features such as user management, product catalog, order processing, and more.

## Prerequisites
- Java 17
- Maven 3.6+
- PostgreSQL

## Setup

### Database Configuration
Ensure PostgreSQL is installed and running. Update the database configuration in `src/main/resources/application.yml` if necessary.

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/jackie_shop
    username: postgres
    password: postgres
```

### Build and Run
1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/ecommerce-shop.git
    cd ecommerce-shop
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    ```sh
    mvn spring-boot:run
    ```

The application will be accessible at `http://localhost:8080/api`.

## Dependencies
The project uses the following dependencies:
- Spring Boot Starter Data JPA
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Web
- PostgresSQL JDBC Driver
- Lombok
- Spring Boot DevTools
- Spring Boot Starter Test

## Configuration
Configuration files are located in `src/main/resources`:
- `application.properties`
- `application.yml`

## License
This project is licensed under the MIT License.

## Authors
- [nhatcoi aka jackie](https://github.com/nhatcoi)
```