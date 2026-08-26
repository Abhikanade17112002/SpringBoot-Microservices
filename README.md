Absolutely. Based on the latest completion, I would update the README to include **Payment Service, Notification Service, AWS SES, AWS S3, image management, booking/payment integration, schedulers, refunds, delivery tracking, and production-oriented resilience**.

I would also update the architecture and startup order accordingly.

````markdown
# 🏨 Hotel Booking Microservices Platform

A production-oriented **Hotel Booking Microservices Platform** built using **Spring Boot Microservices** following **Domain-Driven Design (DDD)** principles.

The platform demonstrates a scalable microservice architecture with service discovery, centralized configuration, API Gateway, JWT authentication, secure inter-service communication, fault tolerance, payment processing, notifications, AWS S3 media storage, and AWS SES email delivery.

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
- Independent Database per Microservice
- Hotel Image Management using Amazon S3
- User Profile Image Management using Amazon S3
- Public Hotel Images
- Private User Images using Presigned URLs
- Payment Processing
- Payment Retry & Failure Handling
- Payment Refund Processing
- Booking Payment Integration
- Booking Expiry Scheduler
- Notification Service
- AWS SES Email Integration
- Notification Delivery Tracking
- Notification Retry Handling
- Notification Scheduler
- Circuit Breaker and Retry for Email Delivery
- End-to-End Service Integration

---

# 🏗️ Microservices

## User Service

Responsible for:

- User Registration
- Login
- User Management
- JWT Generation
- Role Management
- User Profile Management
- User Profile Image Upload
- User Profile Image Retrieval
- User Profile Image Deletion
- Private User Images using S3 Presigned URLs

User images are stored in Amazon S3 under:

```text
users/{userId}/{imageId}.extension
````

User image objects remain private and are accessed through temporary **Presigned URLs**.

---

## Hotel Service

Responsible for:

* Hotel CRUD Operations
* Hotel Search
* Hotel Details Management
* Hotel Image Management
* Hotel Image Upload
* Hotel Image Retrieval
* Hotel Image Deletion
* Primary Hotel Image Management
* Hotel Image Ordering

Hotel images are stored in Amazon S3 under:

```text
hotels/{hotelId}/{imageId}.extension
```

Hotel images are publicly readable while upload and deletion operations remain restricted to the application.

---

## Rating Service

Responsible for:

* Hotel Ratings
* User Reviews
* Average Rating Calculation
* Rating Management

---

## Booking Service

Responsible for:

* Hotel Booking
* Booking Creation
* Booking Status Management
* Booking History
* Booking Payment Integration
* Payment Retry Flow
* Booking Cancellation
* Booking Expiry
* Booking Expiry Scheduler
* Refund Integration
* Booking Validation
* Customer Authorization

### Booking Payment Flow

```text
Customer
    │
    ▼
Booking Service
    │
    ├── Create PENDING Booking
    │
    ▼
Payment Service
    │
    ├── SUCCESS
    │      ↓
    │   CONFIRMED
    │
    └── FAILURE
           ↓
        PENDING
           ↓
      Customer Retry
```

### Booking Expiry

Pending bookings are automatically cancelled when their payment window expires.

```text
PENDING
   │
   │ payment expiry
   ▼
CANCELLED
```

---

## Payment Service

Responsible for:

* Payment Processing
* Payment Status Management
* Payment History
* Payment Retrieval
* Payment by Booking
* Payment by Customer
* Payment by Transaction
* Payment by Gateway Reference
* Payment by Status
* Payment by Payment Method
* Date Range Payment Search
* Payment Refund
* Booking Refund Integration
* Payment Attempt Tracking
* Payment Failure Handling
* Payment Gateway Integration
* Retry Handling
* Circuit Breaker
* Global Exception Handling

### Payment Flow

```text
Booking Service
       │
       ▼
Payment Service
       │
       ▼
Payment Gateway
       │
 ┌─────┴─────┐
 ▼           ▼
SUCCESS     FAILURE
 │           │
 ▼           ▼
CONFIRMED   RETRY
```

---

## Notification Service

Responsible for:

* Booking Notifications
* Payment Notifications
* Notification Templates
* Email Delivery
* Notification Delivery Tracking
* Failed Notification Handling
* Notification Retry
* Notification Scheduler
* Notification Status Management

Supported notification scenarios include:

* Booking Confirmation
* Booking Failure
* Payment Success
* Payment Failure
* Booking Cancellation
* Booking Expiration
* Payment Refund
* Booking Reminder

---

# 📧 AWS SES Email Integration

Notification Service uses **Amazon Simple Email Service (AWS SES)** for email delivery.

Implemented features:

* AWS SES SDK v2
* SES Email Sending
* Sender Verification
* Email Delivery Tracking
* AWS Exception Handling
* Retry Mechanism
* Circuit Breaker
* Failed Notification Handling
* Notification Scheduler
* End-to-End Email Testing

Email flow:

```text
Booking / Payment Service
          │
          ▼
Notification Service
          │
          ▼
Email Service
          │
          ├── Retry
          ├── Circuit Breaker
          └── Exception Handling
          │
          ▼
       AWS SES
          │
          ▼
      Recipient
```

---

# 🖼️ Amazon S3 Image Storage

Amazon S3 is used for storing hotel and user images.

A single S3 bucket is used with separate object prefixes:

```text
spring-boot-microservice-application
│
├── hotels/
│   └── {hotelId}/
│       ├── image-1.jpg
│       ├── image-2.jpg
│       └── image-3.webp
│
└── users/
    └── {userId}/
        └── profile.jpg
```

## Hotel Images

Hotel images are publicly readable:

```text
hotels/*
    ↓
Public GET
    ↓
Browser
```

The application retains control over:

* Upload
* Delete
* Image Metadata
* Primary Image
* Image Ordering

## User Images

User images remain private:

```text
users/*
    ↓
Private S3 Object
    ↓
User Service
    ↓
Presigned URL
    ↓
Client
```

Presigned URLs provide temporary access without exposing AWS credentials.

---

# 🔐 Security

Implemented using Spring Security.

* JWT Authentication
* Stateless Authentication
* Role-Based Authorization
* Protected REST APIs
* API Gateway Authentication
* User Context Propagation
* Secure Internal APIs
* Customer Ownership Validation
* Hotel Authorization
* Private S3 User Images
* Controlled S3 Upload/Delete Operations

---

# 🔄 Inter-Service Communication

Implemented using:

* OpenFeign
* Internal REST APIs
* Eureka Service Discovery
* JWT Header Propagation
* Service-to-Service Validation

Example:

```text
Booking Service
      │
      ▼
Payment Service
      │
      ▼
Notification Service
```

---

# ⚡ Fault Tolerance

Implemented using **Resilience4j**.

* Circuit Breaker
* Retry Mechanism
* Failure Handling
* Graceful Service Recovery
* External Service Failure Handling
* AWS SES Failure Handling
* Payment Failure Handling

Example:

```text
Service Call
     │
     ▼
 Retry
     │
     ▼
Circuit Breaker
     │
 ┌───┴────┐
 ▼        ▼
Success  Failure
          │
          ▼
     Graceful Response
```

---

# ⏱️ Schedulers

The platform currently contains scheduled background processing.

## Booking Expiry Scheduler

Automatically identifies expired `PENDING` bookings and changes them to:

```text
CANCELLED
```

Flow:

```text
PENDING Booking
      │
      │ Payment Expiry Time Reached
      ▼
Booking Expiry Scheduler
      │
      ▼
CANCELLED
```

## Notification Scheduler

Handles pending/failed notifications and retries eligible notification deliveries.

---

# 💳 Payment & Booking Integration

Booking and Payment Services are integrated.

### Initial Booking

```text
Create Booking
      │
      ▼
PENDING
      │
      ▼
Payment Service
      │
 ┌────┴─────┐
 ▼          ▼
SUCCESS    FAILURE
 │          │
 ▼          ▼
CONFIRMED  PENDING
             │
             ▼
           RETRY
```

### Payment Retry

Payment attempts are tracked against the booking.

Retry is allowed only while:

* Booking is still `PENDING`
* Payment window has not expired
* Maximum payment attempts have not been reached

---

# 💰 Refund Flow

Confirmed bookings support cancellation and refund processing.

```text
CONFIRMED Booking
       │
       ▼
Cancellation
       │
       ▼
Payment Service
       │
       ▼
Refund
       │
       ▼
REFUNDED
```

Refund-related notifications are also supported through Notification Service.

---

# 📦 Technology Stack

| Technology           | Usage                          |
| -------------------- | ------------------------------ |
| Java                 | Programming Language           |
| Spring Boot          | Microservices Framework        |
| Spring Security      | Authentication & Authorization |
| Spring Cloud Gateway | API Gateway                    |
| Spring Cloud Config  | Centralized Configuration      |
| Eureka Server        | Service Discovery              |
| OpenFeign            | Service Communication          |
| Resilience4j         | Circuit Breaker & Retry        |
| Spring Data JPA      | Persistence                    |
| Hibernate            | ORM                            |
| MySQL                | Database                       |
| PostgreSQL           | Database Support               |
| Redis                | Rate Limiting                  |
| JWT                  | Authentication                 |
| Amazon S3            | Image Storage                  |
| AWS SDK v2           | AWS Integration                |
| Amazon SES           | Email Delivery                 |
| Maven                | Dependency Management          |
| Git                  | Version Control                |
| Postman              | API Testing                    |

---

# 📁 Project Structure

```text
Hotel-Booking-Microservices
│
├── API-Gateway
├── Config-Server
├── Eureka-Server
│
├── User-Service
├── Hotel-Service
├── Rating-Service
├── Booking-Service
├── Payment-Service
├── Notification-Service
│
└── README.md
```

---

# 🛠 Architecture

```text
                              Client
                                │
                                ▼
                         ┌─────────────┐
                         │ API Gateway │
                         └──────┬──────┘
                                │
             ┌──────────────────┼──────────────────┐
             │                  │                  │
             ▼                  ▼                  ▼
       User Service       Hotel Service      Rating Service
             │                  │
             │                  │
             └─────────┐        │
                       ▼        ▼
                    Booking Service
                           │
                           ▼
                    Payment Service
                           │
                           ▼
                  Notification Service
                           │
                           ▼
                         AWS SES


             ┌─────────────────────────────┐
             │        AWS S3               │
             │                             │
             │ hotels/* → Public Images    │
             │ users/*  → Private Images   │
             └─────────────────────────────┘


             ┌─────────────────────────────┐
             │       Eureka Server         │
             │   Service Registration      │
             │   Service Discovery         │
             └─────────────────────────────┘


             ┌─────────────────────────────┐
             │    Config Server            │
             │ Centralized Configuration   │
             └─────────────────────────────┘
```

---

# 🔄 Application Flow

### Authentication

```text
Client
  │
  ▼
API Gateway
  │
  ├── Validate JWT
  │
  ▼
Microservice
```

### Hotel Booking

```text
Client
  │
  ▼
API Gateway
  │
  ▼
Booking Service
  │
  ├── Validate Customer
  ├── Validate Hotel
  ├── Create PENDING Booking
  │
  ▼
Payment Service
  │
 ┌┴──────────────┐
 ▼               ▼
SUCCESS         FAILURE
 │               │
 ▼               ▼
CONFIRMED       PENDING
                  │
                  ▼
                Retry
```

### Notification

```text
Booking / Payment
       │
       ▼
Notification Service
       │
       ▼
AWS SES
       │
       ▼
Customer Email
```

### Image Management

```text
Client
  │
  ▼
User / Hotel Service
  │
  ▼
Amazon S3
```

---

# 📌 REST API Features

The platform provides:

* CRUD Operations
* Pagination
* Sorting
* Filtering
* Validation
* Global Exception Handling
* Soft Delete
* Standard Response Structures
* Internal Service APIs
* Public APIs
* Payment APIs
* Refund APIs
* Booking APIs
* Notification APIs
* Image Upload APIs
* Image Retrieval APIs
* Image Deletion APIs

---

# 🗄 Database

Each business microservice maintains its own database to ensure loose coupling and database isolation.

Example:

```text
User Service
    └── UserDB

Hotel Service
    └── HotelDB

Rating Service
    └── RatingDB

Booking Service
    └── BookingDB

Payment Service
    └── PaymentDB

Notification Service
    └── NotificationDB
```

Microservices communicate through APIs rather than directly accessing another service's database.

---

# ☁️ AWS Integrations

The platform currently integrates with:

## Amazon S3

Used for:

* Hotel Images
* User Profile Images
* Public Hotel Image Access
* Private User Image Access
* Presigned URLs

## Amazon SES

Used for:

* Booking Emails
* Payment Emails
* Cancellation Emails
* Expiration Emails
* Refund Emails
* Notification Delivery

---

# 🧪 Testing

The application has been tested across:

* REST APIs
* Service-to-Service Communication
* Booking Payment Integration
* Payment Retry
* Booking Expiry Scheduler
* Refund Flow
* Notification Integration
* AWS SES Email Delivery
* Notification Retry
* Circuit Breaker
* Notification Scheduler
* S3 Image Upload
* S3 Image Retrieval
* S3 Image Deletion
* Public Hotel Images
* Private User Images
* Presigned URL Access
* Global Exception Handling

---

# 🔥 Key Highlights

* Production-Oriented Microservice Architecture
* Domain-Driven Design
* Secure JWT Authentication
* Role-Based Access Control
* API Gateway
* Eureka Service Discovery
* Centralized Configuration
* OpenFeign Service Communication
* Resilience4j Circuit Breaker
* Resilience4j Retry
* Redis Rate Limiting
* Independent Databases
* Booking & Payment Integration
* Payment Retry and Refund Processing
* Booking Expiry Scheduler
* Notification Service
* AWS SES Email Delivery
* Notification Retry & Scheduler
* Amazon S3 Image Storage
* Public Hotel Images
* Private User Images with Presigned URLs
* Global Exception Handling
* End-to-End Service Integration
* Scalable and Maintainable Architecture

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

Run the infrastructure services first:

1. Config Server
2. Eureka Server

Then:

3. API Gateway
4. User Service
5. Hotel Service
6. Rating Service
7. Booking Service
8. Payment Service
9. Notification Service

---

# 👨‍💻 Author

**Abhishek Kanade**

Java Backend Developer | Spring Boot | Microservices | Spring Security | REST APIs

````

### One notable change

Your original README was still describing the project primarily as **User → Hotel → Rating → Booking**. The current application has evolved significantly beyond that.

The biggest additions are now:

```text
Payment Service
       ↓
Booking ↔ Payment
       ↓
Refunds

Notification Service
       ↓
AWS SES
       ↓
Retry + CB + Scheduler

S3
├── hotels/* → Public
└── users/*  → Private + Presigned URL
````

So this version represents the **actual current state of the project much more accurately** rather than the earlier architecture.
