# TechTicket Backend

This repository contains the **backend** for the TechTicket project, a job management and ticketing application designed for maintenance companies.

The backend is built with **Spring Boot** and provides APIs for managing customers, tickets, employees, and materials. It integrates with a MySQL database and AWS services for image uploads and hosting.

---

## Features

- **RESTful APIs**: Exposes endpoints for customers, tickets, employees, and materials.
- **Database Integration**: MySQL with JPA for efficient data management.
- **Image Upload**: Uses a Lambda functions and Amazon S3 for storing ticket images.
- **Secure Authentication** (Planned): Spring Security with OAuth 2.0 for modern authentication.

---

## Tech Stack

- **Backend Framework**: Spring Boot
- **Database**: MySQL
- **Storage**: Amazon S3
- **Hosting**: Amazon EC2
- **Containerization**: Docker

---

## Installation & Setup

### Prerequisites

- Java 17+
- MySQL database
- Maven

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-org/techticket-api.git
   cd techticket-api

2. Configure the database in application.properties:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/techticket
   spring.datasource.username=your_db_username
   spring.datasource.password=your_db_password

3. Run the application:
   ```bash
   ./mvn spring-boot:run

4. Access the API at http://localhost:8080
