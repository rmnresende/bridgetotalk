# TalkBridge - Customer Engagement Platform 🌉

[![Java Version](https://img.shields.io/badge/Java-25-blue.svg)](https://www.oracle.com/java/technologies/javase/25-downloads.html)
[![Spring Boot Version](https://img.shields.io/badge/Spring_Boot-4.x-green.svg)](https://spring.io/projects/spring-boot)
[![Database](https://img.shields.io/badge/Database-PostgreSQL-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-lightgrey.svg)](LICENSE)

## 🌟 Overview

**BridgetoTalk** is a modern, scalable, multi-tenant backend application designed to centralize and manage customer service conversations across various external channels (WhatsApp, Telegram, etc.). It serves as a comprehensive portfolio project demonstrating professional software architecture, domain modeling, and cloud-native readiness.

The core function is to route incoming customer messages to available human agents through a dynamic queue system, managing the entire lifecycle of a conversation (from **WAITING_IN_QUEUE** to **CLOSED**).

---

## 🏗️ Architecture and Design

This project is built using the **Hexagonal Architecture (Ports and Adapters)** principle. This structure ensures:

1.  **Domain Isolation:** The core business logic is independent of infrastructure details (e.g., Spring framework, JPA, database).
2.  **Testability:** The Application and Domain layers can be unit-tested without external dependencies.
3.  **Future-Proofing:** Easy adaptation to new technologies (e.g., migrating from PostgreSQL to DynamoDB or adding Kafka for messaging).

### Project Structure (Hexagonal)

The main package structure (`com.renanresende.bridgetotalk`) follows the Hexagonal layers:

* `...domain`: **Core Business Entities** and Enums (`Agent`, `Queue`, `Conversation`). No framework dependencies.
* `...application`: **Use Cases** (Business Logic). Contains **Ports** (interfaces `port.in` and `port.out`) and their **Services** (implementations).
* `...infrastructure`: **Adapters**. Handles technical details like **Web Controllers** (`web`), **Persistence** (`persistence` using Spring Data JPA), and **External Integrations** (`integration`).

## 🛠️ Technology Stack

* **Language:** Java 25
* **Framework:** Spring Boot 3
* **Database:** PostgreSQL (using UUIDs for primary keys)
* **Build Tool:** Maven

---

## ⚙️ Initial Setup

### Prerequisites

* Java 25 JDK
* Maven 3.8+
* PostgreSQL Server (Local or Docker)

### 1. Database Configuration

Create a PostgreSQL database (e.g., `talkbridge_db`).

Configure the connection details in your `application.properties` (or `application.yml`):

```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/talkbridge_db
spring.datasource.username=your_postgres_user
spring.datasource.password=your_postgres_password
spring.jpa.hibernate.ddl-auto=update # Use 'validate' or 'none' in production
spring.jpa.show-sql=true0000000,