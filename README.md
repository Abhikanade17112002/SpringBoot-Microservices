# 🏨 Hotel Booking Microservices Platform

A production-oriented **Hotel Booking Microservices Platform** built using **Spring Boot Microservices** following **Domain-Driven Design (DDD)** principles. The application demonstrates scalable microservice architecture with service discovery, centralized configuration, API Gateway, authentication, fault tolerance, and secure inter-service communication.

---

# 🚀 Features

- Microservices-based architecture
- Domain-Driven Design (DDD)
- Service Discovery using Eureka
- API Gateway with Spring Cloud Gateway
- JWT Authentication
- Role-Based Access Control (RBAC)
- Secure Inter-Service Communication
- OpenFeign Clients
- Circuit Breaker & Retry using Resilience4j
- Redis Rate Limiting
- Centralized Configuration using Config Server
- RESTful APIs
- Pagination & Filtering
- Global Exception Handling
- Request Validation
- Soft Delete Support
- Independent Databases for each Microservice

---

# 🏗️ Microservices

## User Service
Responsible for

- User Registration
- Login
- User Management
- JWT Generation
- Role Management

---

## Hotel Service

Responsible for

- Hotel CRUD Operations
- Hotel Search
- Hotel Details Management

---

## Rating Service

Responsible for

- Hotel Ratings
- User Reviews
- Average Rating Calculation

---

## Booking Service

Responsible for

- Room Booking
- Booking Status
- Booking History
- Booking Workflow

---

## API Gateway

Responsibilities

- Single Entry Point
- JWT Authentication
- Request Routing
- Global Filters
- Request Logging
- Rate Limiting using Redis

---

## Eureka Server

- Service Registration
- Service Discovery
- Dynamic Load Balancing

---

## Spring Cloud Config Server

Provides

- Centralized Configuration
- Environment Specific Properties
- External Configuration Management

---

# 🔐 Security

Implemented using Spring Security

- JWT Authentication
- Stateless Authentication
- Role-Based Authorization
- Protected REST APIs
- Secure API Gateway
- User Context Propagation between Services

---

# 🔄 Inter-Service Communication

Implemented using

- OpenFeign Clients
- Internal REST APIs
- Service Discovery via Eureka
- JWT Header Propagation

---

# ⚡ Fault Tolerance

Implemented using **Resilience4j**

- Circuit Breaker
- Retry Mechanism
- Graceful Service Recovery
- Failure Handling

---

# 📦 Technology Stack

| Technology | Usage |
|------------|-------|
| Java | Programming Language |
| Spring Boot | Microservices Framework |
| Spring Security | Authentication & Authorization |
| Spring Cloud Gateway | API Gateway |
| Spring Cloud Config | Centralized Configuration |
| Eureka Server | Service Discovery |
| OpenFeign | Service Communication |
| Resilience4j | Circuit Breaker & Retry |
| Spring Data JPA | Persistence |
| Hibernate | ORM |
| MySQL | Database |
| Redis | Rate Limiting & Caching |
| JWT | Authentication |
| Maven | Dependency Management |
| Git | Version Control |
| Postman | API Testing |

---

# 📁 Project Structure

```
Hotel-Booking-Microservices
│
├── API-Gateway
├── Config-Server
├── Eureka-Server
├── User-Service
├── Hotel-Service
├── Rating-Service
├── Booking-Service
│
└── README.md
```

---

# 🛠 Architecture

```
                    Client
                       │
                       ▼
                API Gateway
                       │
      ┌────────────────┼────────────────┐
      │                │                │
      ▼                ▼                ▼
 User Service     Hotel Service    Booking Service
      │                │                │
      └────────────┐   │   ┌────────────┘
                   ▼   ▼   ▼
              Rating Service

        ▲
        │
 Eureka Service Registry

        ▲
        │
 Spring Cloud Config Server
```

---

# 🔄 Application Flow

1. Client sends request to API Gateway.
2. Gateway validates JWT Token.
3. Request is routed to the appropriate microservice.
4. Services communicate using OpenFeign.
5. Eureka resolves service locations.
6. Config Server provides centralized configuration.
7. Resilience4j handles failures using Circuit Breaker and Retry.
8. Response is returned through the Gateway.

---

# 📌 REST API Features

- CRUD Operations
- Pagination
- Sorting
- Filtering
- Validation
- Exception Handling
- Soft Delete
- Standard Response Structure

---

# 🗄 Database

Each microservice maintains its own independent MySQL database to ensure loose coupling and database isolation.

Example:

- UserDB
- HotelDB
- RatingDB
- BookingDB

---

# 🔥 Key Highlights

- Production-Oriented Architecture
- Secure JWT Authentication
- Role-Based Access Control
- Microservice Communication with OpenFeign
- API Gateway
- Service Discovery
- Centralized Configuration
- Fault Tolerance using Resilience4j
- Redis Rate Limiting
- Independent Databases
- Scalable and Maintainable Design

---

# ▶️ Getting Started

## Clone Repository

```bash
git clone https://github.com/your-username/hotel-booking-microservices.git
```

---

## Build Project

```bash
mvn clean install
```

---

## Start Services

Run services in the following order:

1. Config Server
2. Eureka Server
3. API Gateway
4. User Service
5. Hotel Service
6. Rating Service
7. Booking Service

---

# 👨‍💻 Author

**Abhishek Kanade**

Java Backend Developer | Spring Boot | Microservices | Spring Security | REST APIs

---
