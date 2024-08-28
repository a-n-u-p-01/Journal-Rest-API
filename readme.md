

# Journal REST API

## Overview
The Journal REST API is a Spring Boot application designed to manage journal entries for users. It provides endpoints for creating, reading, updating, and deleting journal entries, as well as user management functionalities.

## Features
- User Authentication and Authorization
- CRUD operations for Journal Entries
- User Management
- Health Check Endpoint
- MongoDB Integration

## Technologies Used
- Java 17 or Higher
- Spring Boot 3.3.2
- Spring Data MongoDB
- Spring Security
- Lombok
- Maven

## Getting Started

### Prerequisites
- Java 17
- Maven
- MongoDB

### Installation

1. **Clone the repository:**
    ```sh
    git clone https://github.com/yourusername/Journal-Rest-API.git
    cd Journal-Rest-API
    ```

2. **Build the project:**
    ```sh
    mvn clean install
    ```

3. **Run the application:**
    ```sh
    mvn spring-boot:run
    ```

### Configuration
The application uses a MongoDB database. Ensure that MongoDB is running and accessible. You can configure the MongoDB connection in the `application.properties` file.

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/yourdatabase
```

## API Endpoints

### Public Endpoints
- **Create User:** `POST /public/create-user`
    - Request Body:
    ```json
    {
        "userName": "string",
        "password": "string"
    }
    ```

### User Endpoints
- **Get All Journals:** `GET /journal`
- **Get Journal by ID:** `GET /journal/{id}`
- **Create Journal:** `POST /journal`
    - Request Body:
    ```json
    {
        "title": "string",
        "content": "string"
    }
    ```
- **Update Journal:** `PUT /journal/{id}`
    - Request Body:
    ```json
    {
        "title": "string",
        "content": "string"
    }
    ```
- **Delete Journal:** `DELETE /journal/{id}`

### Admin Endpoints
- **Get All Users:** `GET /admin`
- **Delete User:** `DELETE /admin/{userName}`

### Health Check
- **Check Health:** `GET /health`

## Security
The application uses Spring Security for authentication and authorization. The following roles are defined:
- **USER:** Can manage their own journal entries.
- **ADMIN:** Can manage users and their journal entries.

## Contributing
Contributions are welcome! Please fork the repository and submit a pull request.
Create an issue first before PR.

### For Spring Boot Learners
If you are a Spring Boot learner, we encourage you to debug this application and contribute to its improvement. Here are some steps to get started:
1. **Set up your development environment:** Follow the installation steps above to set up the project locally.
2. **Explore the codebase:** Familiarize yourself with the structure of the project and the different components.
3. **Debug the application:** Use your IDE's debugging tools to step through the code and understand how it works.
4. **Identify areas for improvement:** Look for bugs, performance issues, or areas where the code can be refactored.
5. **Contribute:** Fork the repository, make your changes, and submit a pull request.