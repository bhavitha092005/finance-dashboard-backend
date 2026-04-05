# 💰 Finance Dashboard Backend

## 📌 Overview

This project is a backend system for managing financial transactions with secure authentication and role-based access control. It provides APIs for transaction management and analytical dashboard insights such as income, expenses, and category-wise breakdown.

The system is designed following clean backend architecture principles and focuses on scalability, maintainability, and secure data handling.

---

## 🚀 Features

### 🔐 Authentication & Security

* User Registration & Login using JWT
* Password encryption using BCrypt
* Stateless authentication
* Secure API access using Bearer tokens

### 👥 Role-Based Access Control

* **Viewer** → Read-only access
* **Analyst** → Access to analytics/dashboard
* **Admin** → Full access (CRUD + all data)

---

### 💳 Transaction Management

* Create, update, delete transactions
* Fetch all transactions (user-specific)
* Filter transactions by:

  * Type (Income / Expense)
  * Category
  * Date range

---

### 📊 Dashboard Analytics

* Total Income
* Total Expenses
* Net Balance
* Category-wise aggregation
* Recent transactions

---

### 🛡️ Data Security

* Sensitive fields (like passwords) are never exposed in API responses
* DTOs used to control output data
* User-specific data isolation implemented

---

## 🏗️ Architecture

```
Controller → Service → Repository → Database
```

### Layers:

* **Controller** → Handles API requests/responses
* **Service** → Business logic & validations
* **Repository** → Database interaction (JPA)
* **Security** → JWT + authentication filter

---

## 🧠 Key Design Decisions

* Used **JWT** for stateless authentication to avoid server-side session storage
* Implemented **role-based access** at service layer for flexibility
* Used **DTOs** to prevent exposing sensitive data
* Aggregation logic handled in service layer for better control

---

## ⚙️ Tech Stack

* Java 17
* Spring Boot
* Spring Security
* JWT (jjwt)
* MySQL
* JPA / Hibernate

---

## 📂 API Endpoints

### 🔐 Auth APIs

* `POST /api/auth/register`
* `POST /api/auth/login`

---

### 💳 Transaction APIs

* `POST /api/transactions`
* `GET /api/transactions`
* `GET /api/transactions/{id}`
* `PUT /api/transactions/{id}`
* `DELETE /api/transactions/{id}`
* `GET /api/transactions/filter`

---

### 📊 Dashboard APIs

* `GET /api/dashboard/summary`

---

## 🔄 Sample Flow

1. Register a user
2. Login → receive JWT token
3. Use token in headers:

   ```
   Authorization: Bearer <token>
   ```
4. Access protected APIs

---

## ⚠️ Assumptions

* Each transaction belongs to a single user
* Admin can access all user data
* Other roles can access only their own data

---

## ⚖️ Trade-offs

* No caching implemented for dashboard (computed in-memory)
* Filtering handled in service layer instead of optimized DB queries
* Basic role model (ENUM) instead of dynamic permission system

---

## 🛠️ Setup Instructions

1. Clone repository
2. Create MySQL database:

   ```
   CREATE DATABASE finance_db;
   ```
3. Update `application.properties`:

   ```
   spring.datasource.username=root
   spring.datasource.password=bhavitha123@
   ```
4. Run the application
5. Use Postman to test APIs

---

## 🧪 Testing

* Tested using Postman
* Verified:

  * Authentication flow
  * Role-based access
  * CRUD operations
  * Dashboard aggregation

---

## 📌 Future Improvements

* Add caching (Redis) for dashboard performance
* Implement pagination
* Add API documentation (Swagger)
* Improve filtering with database-level queries
* Add unit & integration tests

---

## 👩‍💻 Author

**Bhavitha Pala**

* GitHub: https://github.com/bhavitha092005
* LinkedIn: https://linkedin.com/in/bhavitha-pala

---
