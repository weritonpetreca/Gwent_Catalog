# Gwent Catalog

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?logo=spring-boot)
![Maven](https://img.shields.io/badge/Maven-Build-orange?logo=apache-maven)

## 📚 About the Project

**Gwent Catalog** is a Java Spring Boot application that allows users to explore, search, and manage cards from the Gwent card game. The project demonstrates skills in backend development, RESTful APIs, database integration, and clean code practices.

---

## ✨ Features

- 🔍 **Search** for Gwent cards by name, faction, or rarity
- ⭐ **Mark favorites** and manage your favorite cards
- 🗂️ **Filter** cards using advanced search options
- 🖥️ **Console interface** for easy interaction
- 💾 **Persistence** with MySQL or H2 (for local development)
- 📦 **REST API** integration for fetching card data

---

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.5.0**
- **Spring Data JPA**
- **Maven**
- **MySQL / H2 Database**
- **Lombok**

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL (optional, for production)

### Running Locally

1. **Clone the repository:**
   ```bash
   git clone https://github.com/weritonpetreca/Gwent_Catalog.git
   cd Gwent_Catalog

2. **Configure the database:**

* For local development, H2 is supported out of the box.
* For MySQL, update src/main/resources/application.properties with your credentials.

3. **Build and run:**
   ```bash
   mvn spring-boot:run
   
4. **Use the console interface to interact with the catalog.**
<hr></hr>

## 📁 Project Structure
  ```bash
  src/
   └── main/
       ├── java/com/petreca/gwent_catalog/
       │   ├── config/         # Spring configurations
       │   ├── console/        # Console interface and handlers
       │   ├── dto/            # Data Transfer Objects
       │   ├── model/          # JPA Entities
       │   ├── repository/     # Spring Data Repositories
       │   ├── service/        # Business logic
       │   └── util/           # Utilities
       └── resources/
           ├── application.properties
           └── static/
  ````
<hr></hr>

## 💡 Why This Project?

This project was built to showcase my skills in Java and Spring Boot, focusing on clean architecture, best practices, and real-world features. It is part of my journey to become a professional software developer.
<hr></hr>

## 👨‍💻 Author

### Weriton L. Petreca:
[LinkedIn](https://www.linkedin.com/in/weriton-petreca/)
[GitHub](https://github.com/weritonpetreca)
<hr></hr>
⭐ If you like this project, give it a star!
