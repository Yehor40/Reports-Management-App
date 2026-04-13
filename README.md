# ReportManagementApp 🚀

A robust, enterprise-grade remuneration record-keeping system built with **Clean Architecture** and **CQRS**. This application simplifies administrative workflows by providing a secure, high-performance platform for managing project evidence and user activities.

## ✨ Features
- **Modern SPA Frontend**: Built with React using Class Components and a premium **Glassmorphism** design system.
- **Clean Architecture**: Strong isolation between Domain, Application, Infrastructure, and Web layers.
- **CQRS Pattern**: Decoupled Command and Query handlers for better scalability and maintainability.
- **Advanced Security**: 
    - Stateless **JWT Authentication**.
    - Defense-in-depth protection against **IDOR** (Insecure Direct Object Reference).
    - Granular Role-Based Access Control (Admin vs. User).
- **Reporting**: Export and download filtered evidence records in **Excel (.xlsx)** format.
- **Containerized**: Fully Dockerized for seamless deployment.

## 🛠️ Technology Stack
- **Backend**: Spring Boot 3.0, Spring Security 6, Hibernate, PostgreSQL.
- **Frontend**: React (Class Components), Vite, Axios, React Router.
- **DevOps**: Docker, Docker Compose.
- **Reporting**: Apache POI.

## 🚀 Quick Start (Docker)

Ensure you have [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) installed.

1. **Clone and Run**:
   ```bash
   docker-compose up --build
   ```
2. **Access the App**:
   👉 [http://localhost:8080](http://localhost:8080)

## 🔑 Initial Credentials (Auto-Seeded)

The database is automatically seeded with the following accounts for testing:

| Role  | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@example.com` | `pass123` |
| **User 1** | `user1@example.com` | `pass123` |
| **User 2** | `user2@example.com` | `pass123` |

## 🏗️ Architectural Overview

The project follows the principles of **Clean Architecture**:
- **Domain**: Pure business logic and entities. No dependencies.
- **Application**: CQRS Handlers, DTOs, and Mappers.
- **Infrastructure**: Database repositories, JWT implementation, and Excel exporters.
- **Web**: REST Controllers and the React Single Page Application.

## 🌍 Environment Variables

You can customize the configuration using the `.env` file:
- `DB_URL`: PostgreSQL connection string.
- `DB_USERNAME`: Database user.
- `DB_PASSWORD`: Database password.

---
*Created as part of architectural modernization and security hardening.*
