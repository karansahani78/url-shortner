
````
# 🔗 URL Shortener Service

A lightweight and efficient **Spring Boot** based URL shortener API, ideal for rapid link generation and redirection. Perfect for learning, prototyping, or light production use.

---

## 🚀 Tech Stack

- **Java 17**  
- **Spring Boot**  
- **Spring Data JPA** (H2 file-based DB or any SQL DB)  
- **Lombok**, **MapStruct**  
- **Maven**

---

## ⚙️ Getting Started

### ✅ Prerequisites

- Java 17+  
- Maven 3.6+  
- (Optional) Docker (for running a database)

### 🛠️ Setup & Run

```bash
git clone https://github.com/karansahani78/url-shortner.git
cd url-shortner
````

#### 1.  Configure your database (optional; defaults to H2):

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:file:./data/db
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

#### 2.  Run the server:

```bash
mvn clean install
mvn spring-boot:run
```

The API will launch on `http://localhost:8080`.

---

## 📚 API Endpoints

### **POST** `/api/shorten`

**Input:**

```json
{ "url": "https://example.com", "customAlias": "exmpl", "expiryDays": 7 }
```

**Response:**

```json
{ "shortUrl": "http://localhost:8080/r/exmpl" }
```

### **GET** `/r/{alias}`

Redirects (HTTP 302) to the original URL.

### **GET** `/api/{alias}`

Returns the original URL in JSON format.

### ✅ Additional features

* Custom alias support
* Optional expiry time
* Alias collision handling
* Metrics tracking (future enhancement)

---

## 💡 How It Works

1. Clients submit a long URL (and optionally alias/expiry).
2. Server generates a unique short alias (UUID, Base62) or uses custom one.
3. Saves mapping in database.
4. Redirect endpoint fetches original URL and responds with 302.

---

## 📁 Project Structure

```
src/main/java/com/karan/urlshortener/
├─ controller/       # API & redirect endpoints
├─ service/          # Business logic (mapping, validation)
├─ repository/       # DB access (JPA repositories)
├─ model/            # JPA entities
├─ dto/              # Request/response models
├─ config/           # Spring configurations
└─ mapper/           # MapStruct DTO mappers
```

---

## 🎯 Use Cases

* Quickly generate and share short links.
* Embed in emails, chat apps, QR codes.
* Custom aliases for branding or tracking.

---

## 🧩 Future Enhancements

* ✅ H2 → MySQL/PostgreSQL support
* 🔐 Rate limiting and abuse protection
* 📊 Analytics: click counts, geolocation
* ⚙️ URL expiration and cleanup jobs
* 🔒 JWT-based access control
* 🚀 Docker-compose setup
* 🧪 Unit & integration tests

---

## 👨‍💻 About the Author

**Karan Sahani** – backend developer passionate about clean code and Spring Boot.
📧 [karansahani723@gmail.com](mailto:karansahani723@gmail.com) · 🌐 [GitHub](https://github.com/karansahani78)

---

## 📜 License

This project is open-sourced under the **MIT License**. Feel free to use and modify it!

```
