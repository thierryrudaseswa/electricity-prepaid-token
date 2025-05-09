
# Electricity Management System

A Spring Boot application for managing electricity tokens and meters, providing functionality for token purchases, meter management, and automated notifications.

## Features

- **Token Management**
  - Purchase electricity tokens
  - Validate tokens
  - Track token expiry
  - View token history

- **Meter Management**
  - Register new meters
  - Associate meters with users
  - View meter details
  - Track meter consumption

- **User Management**
  - User registration and authentication
  - Role-based access control
  - User profile management

- **Notification System**
  - Automated token expiry notifications
  - Email notifications
  - Real-time status updates

## Technical Stack

- **Backend Framework:** Spring Boot
- **Database:** JPA/Hibernate
- **Security:** Spring Security
- **Documentation:** Swagger/OpenAPI
- **Build Tool:** Maven/Gradle
- **Java Version:** 24

## Prerequisites

Before running this application, make sure you have:

- JDK 24 or later installed
- Maven/Gradle build tool installed
- An IDE (preferably IntelliJ IDEA)
- Email server configuration (for notifications)

## Installation

1. Clone the repository:
```
bash git clone https://github.com/NE-PREP-2024-2025/springboot-eucl
``` 

2. Navigate to the project directory:
```bash 
cd pringboot-eucl
``` 

3. Build the project:
```bash 
mvn clean install
``` 

4. Run the application:
```bash
 mvn spring-boot:run
``` 

## API Endpoints
Access Endpoints at swagger at `http:localhost:8080/swagger-ui/index.html`

### User Management
- `GET /api/v1/users/all` - Get all users
- `GET /api/v1/users/{id}` - Get user by ID
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user

### Token Management
- `POST /purchase` - Purchase new token
- `GET /{meterNumber}` - Get tokens by meter number
- `POST /{token}` - Validate token
- `GET /all` - Get all tokens
- `PUT /{token}` - Update token status

## Configuration

### Application Properties

Create an `application.properties` file in `src/main/resources` with the following configurations:
```
properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database spring.datasource.username=your_username spring.datasource.password=your_password
# JPA Properties
spring.jpa.hibernate.ddl-auto=update spring.jpa.show-sql=true
# Email Configuration
spring.mail.host=smtp.gmail.com 
spring.mail.port=587
 spring.mail.username=your-email@gmail.com
 spring.mail.password=your-app-password
 spring.mail.properties.mail.smtp.auth=true
 spring.mail.properties.mail.smtp.starttls.enable=true
``` 

## Security

The application implements role-based access control with the following roles:
- ADMIN: Full system access
- CUSTOMER: Limited access to personal information and token purchases

## Scheduled Tasks

The system includes scheduled tasks for:
- Token expiry checks (runs every 5 minutes)
- Notification processing
- Email delivery


## Error Handling

The system implements global exception handling for:
- Bad Request exceptions
- Authentication failures
- Invalid data inputs
- System errors
- Entity Not found Exception
