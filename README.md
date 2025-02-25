🌟 **TicketEase**

TicketEase is a ticket management system designed for efficient issue tracking. It consists of:

- **TicketEase-GUI**: A Java Swing-based graphical user interface.
- **TicketEase (Backend)**: A Spring Boot backend handling business logic.
- **Database**: Supports MySQL and Oracle.
- **Docker**: Simplifies deployment using Docker Compose.
- **Swagger**: Provides interactive API documentation at Swagger UI.

---

## 📌 Features

👉 Java Swing UI for user-friendly interaction.
👉 Spring Boot backend for high-performance processing.
👉 MySQL / Oracle database support.
👉 Secure authentication with JWT & Spring Security.
👉 RESTful API for smooth integration.
👉 Docker Compose for easy deployment.
👉 Swagger UI for API documentation.
👉 Role-based access control (Employee & IT Support).

---

## 🛠️ Technologies Used

### 🖥️ Backend (Spring Boot)
- **Java (Spring Boot)** – Backend framework.
- **Spring Data JPA** – ORM for database access.
- **Spring Security + JWT** – Authentication & authorization.
- **Lombok** – Reduces boilerplate code.
- **Maven** – Dependency management.
- **Swagger (Springdoc OpenAPI)** – API documentation.

### 🎨 Frontend (Swing GUI)
- **Java Swing** – Desktop GUI framework.
- **JDBC** – Database connectivity.

### 📂 Database
- **MySQL or Oracle** – Relational database.

### 📦 Deployment
- **Docker** – Containerized deployment.
- **Docker Compose** – Multi-container setup for backend and database.

---

## 🚀 Installation & Setup

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/Soufiane-Ait-Elghazi/TicketEase.git
cd TicketEase
```

### 2️⃣ Running with Docker (Recommended)
📌 Prerequisites:
- Install Docker and Docker Compose.

▶️ Run the Application:
```sh
cd TicketEase
docker-compose up -d
```

This will:
👉 Start the Spring Boot backend on port **8888**.
👉 Set up the MySQL/Oracle database.

Backend will be available at: **http://localhost:8888**
Swagger UI: **http://localhost:8888/swagger-ui/index.html**

⏹️ Stop the Application:
```sh
docker-compose down
```

### 3️⃣ Running Manually (Without Docker)
📌 Backend Setup

- Install **JDK 17+**, **Maven**.
- Configure the database in `application.properties`:

#### For MySQL:
```properties
server.port=8888
spring.datasource.url=jdbc:mysql://localhost:3306/ticketease
spring.datasource.username=root
spring.datasource.password=yourpassword
```

#### For Oracle:
```properties
server.port=8888
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:orcl
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Run the backend:
```sh
mvn clean install
mvn spring-boot:run
```

Swagger UI will be available at: **http://localhost:8888/swagger-ui/index.html**

📌 **Frontend (Swing UI) Setup**
- Install **JDK 17+**, **Maven**.
- Run the Swing application:
```sh
cd TicketEase-GUI
mvn clean install
java -jar target/TicketEase-GUI.jar
```

---

## 🔗 API Endpoints

### 📋 Ticket Category Controller
| Method | Endpoint | Description |
|--------|-----------------------------|---------------------------------|
| GET | /ticketEase/ticket-categories/{id} | Retrieve a ticket category by ID |
| PUT | /ticketEase/ticket-categories/{id} | Update a ticket category by ID |
| DELETE | /ticketEase/ticket-categories/{id} | Delete a ticket category by ID |
| POST | /ticketEase/saveTicketCategory | Create a new ticket category |
| GET | /ticketEase/ticket-categories | Retrieve all ticket categories with pagination |

### 📃 Ticket Comment Controller
| Method | Endpoint | Description |
|--------|-------------------------|---------------------------------|
| GET | /ticketEase/comments/{id} | Retrieve a ticket comment by ID |
| PUT | /ticketEase/comments/{id} | Update a ticket comment by ID |
| DELETE | /ticketEase/comments/{id} | Delete a ticket comment by ID |
| POST | /ticketEase/saveTicketComment/{id} | Create a new ticket comment |
| GET | /ticketEase/tickets-comments | Retrieve all ticket comments with pagination |
| GET | /ticketEase/ticket-comments/{id} | Retrieve all comments for a specific ticket |

### 💰 Ticket Controller
| Method | Endpoint | Description |
|--------|----------------------------|---------------------------------|
| POST | /ticketEase/tickets-with-filter | Retrieve tickets by Title |
| POST | /ticketEase/saveTicket | Create a new ticket |
| POST | /ticketEase/change-ticket-status | Change the status of a ticket |
| GET | /ticketEase/tickets | Retrieve all tickets with pagination |
| GET | /ticketEase/ticket/{id} | Retrieve a ticket by ID |
| DELETE | /ticketEase/ticket/{id} | Delete a ticket by ID |

### 🛡️ Security Controller
| Method | Endpoint | Description |
|--------|------------------------|---------------------------------|
| POST | /ticketEase/auth/login | Authenticate user |
| GET | /ticketEase/auth/profile | Retrieve authenticated user profile |

---

## 📑 License
my code is your code

## 📞 Contact
For inquiries or support, reach out at:
📧 soufianeaitelghazi@gmail.com  
🌐 GitHub Repository

---

## ✅ Project Tasks

| Task | Status      | Notes |
|-----------------------------|-------------|----------------|
| **Backend Development** |             | |
| - Set up Spring Boot project | Done        | |
| - Implement RESTful API | Done        | |
| - Integrate Swagger/OpenAPI | Done        | |
| - Implement Ticket Creation | Done        | |
| - Implement Status Tracking | Done        | |
| - Implement User Roles | Done        | |
| - Implement Audit Log | Done        | |
| - Implement Search & Filter | Done        | |
| **Database (Oracle SQL)** |             | |
| - Design Schema | Done        | |
| - Write SQL script for setup | Done        | |
| **UI Development (Swing)** |             | |
| - Set up Swing project | Done        | |
| - Implement Ticket Creation UI | Done        | |
| - Implement Status Management | Done        | |
| - Implement Search & Filter UI | Done        | |
| **Testing** |             | |
| - Write JUnit tests | In progress | |
| - Write Mockito tests | In progress        | |
| **Documentation** |             | |
| - Write README | Done        | |
| - Write API documentation | Done        | |
| **Deployment** |             | |
| - Set up Docker for backend | Done        | |
| - Set up Docker for Oracle DB | Done        | |
| - Package Swing UI as JAR | Done        | |
| **Submission** |             | |
| - Push code to GitHub | Done        | |
| - Ensure README setup guide | Done        | |

---
🔹 Database Initialization

At the start of the application, the database is pre-filled with some fake data for testing purposes. This includes:

Ticket categories (e.g., Network, Hardware, Software)

Sample tickets assigned to these categories

Random comments added to each ticket

This initialization is done using a CommandLineRunner bean in the Spring Boot application.
