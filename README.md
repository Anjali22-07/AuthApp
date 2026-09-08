# AuthApp 🔐

A full-stack authentication and authorization application built with **Spring Boot** and **React**. The project implements secure user authentication using **JWT access tokens, refresh tokens, role-based authorization, and OAuth 2.0**.

## 🚀 Features

* User registration and login
* Secure password hashing using **BCrypt**
* JWT-based authentication
* Access token and refresh token mechanism
* Refresh token-based session renewal
* Logout functionality
* Role-based authorization
* OAuth 2.0 social login
* Protected REST APIs
* Global exception handling
* React-based frontend
* Responsive authentication UI

## 🏗️ Project Structure

```text
AuthApp/
├── auth/                 # Spring Boot backend
│   ├── src/
│   ├── pom.xml
│   └── ...
│
├── auth-frontend/        # React frontend
│   ├── src/
│   ├── public/
│   ├── package.json
│   └── ...
│
├── .gitignore
└── README.md
```

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA / Hibernate
* JWT
* OAuth 2.0
* MySQL
* Maven

### Frontend

* React
* TypeScript
* Vite
* Tailwind CSS
* shadcn/ui
* Axios
* React Router

## 🔐 Authentication Flow

The application uses a token-based authentication architecture.

```text
User
 │
 ├── Register ──> Backend ──> Password hashed with BCrypt
 │
 └── Login ─────> Backend
                    │
                    ├── Access Token
                    └── Refresh Token
                            │
                            ▼
                     Authenticated APIs
```

When the access token expires, the refresh token can be used to obtain a new access token without requiring the user to log in again.

## 🔑 Security

The backend uses **Spring Security** to protect application endpoints.

Security features include:

* BCrypt password hashing
* JWT authentication
* Refresh token validation
* Role-based access control
* OAuth 2.0 authentication
* Protected REST endpoints
* Authentication filters
* Centralized exception handling

## ⚙️ Getting Started

### Prerequisites

Make sure you have the following installed:

* Java 17+
* Maven
* Node.js
* MySQL
* Git

### 1. Clone the repository

```bash
git clone https://github.com/Anjali22-07/AuthApp.git
cd AuthApp
```

### 2. Configure the backend

Navigate to the backend:

```bash
cd auth
```

Configure your database and authentication credentials in the appropriate Spring configuration files.

For example:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/authdb
    username: your_username
    password: your_password
```

**Do not commit passwords, API keys, client secrets, or other sensitive credentials to GitHub.**

### 3. Run the backend

Using Maven:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

### 4. Run the frontend

Open another terminal:

```bash
cd auth-frontend
npm install
npm run dev
```

The frontend will be available at the local Vite development URL shown in the terminal.

## 🔗 API

The backend exposes REST endpoints for authentication and authorization, including operations such as:

| Method | Endpoint                | Description                 |
| ------ | ----------------------- | --------------------------- |
| POST   | `/api/V1/auth/register` | Register a new user         |
| POST   | `/api/V1/auth/login`    | Authenticate user           |
| POST   | `/api/V1/auth/refresh`  | Generate a new access token |
| POST   | `/api/V1/auth/logout`   | Logout user                 |

Additional protected endpoints are available for authenticated users and role-based access.

## 🧪 Testing

Run backend tests with:

```bash
cd auth
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

## 🔮 Future Improvements

* Email verification
* Password reset functionality
* Account management
* Improved OAuth provider support
* Rate limiting for authentication endpoints
* Docker support
* CI/CD pipeline
* Production deployment

## 👩‍💻 Author

**Anjali Singh**

Built as a full-stack authentication project to explore secure authentication, authorization, OAuth 2.0, JWT-based security, and modern React development.
