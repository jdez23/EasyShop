# 🛍️ EasyShop – E-Commerce API (Java Capstone)

This project is a RESTful backend API for an e-commerce platform called **EasyShop**, built as part of a Java bootcamp capstone project. The backend is developed using **Spring Boot**, connected to an **Azure-hosted SQL Server database**, and tested with **Postman**.

---

## ✅ Features

### 🔐 Authentication
- Register and login with role-based access (ADMIN or USER)
- JWT-based authentication integrated into all secure routes

### 📦 Product Catalog
- View all products
- Filter products by:
    - Category
    - Price range (`minPrice` / `maxPrice`)
    - Color
- Admin-only access to:
    - Add new products
    - Update existing products
    - Delete products

### 📁 Categories API (Implemented in Phase 2)
- `GET /categories`: View all categories
- `GET /categories/{id}`: View a specific category
- `POST /categories`: Add a new category (ADMIN only)
- `PUT /categories/{id}`: Update a category (ADMIN only)
- `DELETE /categories/{id}`: Delete a category (ADMIN only)

### 🛠 Bug Fixes
#### 🐛 Bug 1: Broken Product Filter/Search
- Fixed logic in `ProductDao` to ensure filters for category, price, and color work independently and together
- Added test cases to validate filter combinations

#### 🐛 Bug 2: Product Update Creates Duplicate
- Fixed `updateProduct()` logic that incorrectly inserted new rows instead of updating existing ones
- Verified via Postman that updates modify existing products instead of duplicating them

---

## 🧪 Testing with Postman

A Postman collection was created with:
- Login and token automation
- Tests for all product and category endpoints
- Authorization header management using `{{jwt}}` variables

---

## 🗃 Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Security & JWT**
- **JDBC & SQL Server**
- **Postman**
- **Azure Data Studio**
- **Maven**

---


## 🌱 Future Enhancements (Optional Phases)

- Shopping cart (add, update, remove items)
- Profile management (`/profile`)
- Checkout flow (`/orders`)
- Role-based view logic in front-end UI

---

## 👤 Author
Jesse Hernandez
Creative Technologist