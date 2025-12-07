# Inventory Management System

A comprehensive RESTful API for managing inventory items with user authentication, role-based access control, and integration with external product APIs.

## 🔧 Requirements

- **Java 21** or higher
- **Maven 3.6+** (or use the included Maven Wrapper)
- **Docker & Docker Compose**

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd inventory-management-system
```

### 2. Database Setup

Start the database using Docker Compose:

```bash
cd backend
docker-compose up -d
```

This will start:
- PostgreSQL database on port `5432`
- Adminer (database management UI) on port `8080`

### 3. Environment Configuration

1. Navigate to the backend directory:
```bash
cd backend
```

2. Create a `.env` file in the `backend` directory:
```bash
cp local.env .env
```

3. Update the `.env` file with your configuration.

### 4. Build the Project

```bash
cd backend
./mvnw clean install
```

Or on Windows:
```bash
mvnw.cmd clean install
```

### 5. Run Database Migrations

Migrations run automatically on application startup via Flyway. No manual steps required.

### 6. Start the Application

Run the development server script:

```bash
cd backend
chmod +x run-server.sh  # Only needed first time on Unix/Mac
./run-server.sh
```

### 7. Access the Application

Once the application is running, you can access:

- **API Base URL**: `http://localhost:3000/api/v1`
- **Swagger UI**: `http://localhost:3000/docs`
- **OpenAPI JSON**: `http://localhost:3000/api-docs`
- **Adminer (Database UI)**: `http://localhost:8080`

## 📖 Project Description

The Inventory Management System is a Spring Boot-based REST API that provides:

- **User Management**: Registration, authentication, and profile management
- **Inventory Management**: CRUD operations for inventory items with soft-delete support
- **JWT Authentication**: Secure token-based authentication
- **Role-Based Access Control**: Admin and Manager roles with different permissions
- **External API Integration**: Fetch products from external dummy JSON API
- **API Documentation**: Interactive Swagger UI for testing endpoints
- **Database Migrations**: Version-controlled database schema using Flyway

The system follows RESTful principles and implements best practices for security, error handling, and code organization.

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot**
- **Hibernate**
- **PostgreSQL**
- **Docker**
- **Maven**
- **JWT**
- **Swagger/OpenAPI**
