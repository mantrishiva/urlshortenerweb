📎 URL Shortener Service (Spring Boot + Redis)
A simple, high-throughput URL shortening service like TinyURL, built using Spring Boot, Redis, and JPA.

🔧 Features
Shortens long URLs to compact codes (e.g., /abc123)

Supports clean redirection: http://short.ly/abc123 → original URL

Short URLs expire automatically after 30 days

High-speed lookup with Redis caching

Persistent fallback using an in-memory H2 database

🛠 Tech Stack
Java 17+

Spring Boot

Spring Data JPA

Redis

H2 (for demo)

Lombok

🚀 How to Run
Clone the repo

Start Redis (localhost:6379)

Run the Spring Boot app (./mvnw spring-boot:run or from IDE)

Access:

POST http://localhost:8080/api/shorten to shorten a URL

GET http://localhost:8080/api/{code} to redirect

