# NewNowApp-BE — Image Task API

A technical test backend that receives an image, resizes it to requested dimensions, persists task metadata, and (optionally) stores the processed image on disk.

---

## Overview

- **Language / Platform**: Java 17, Spring Boot 3.5.5
- **Style**: Reactive controllers (`Mono<>`) with blocking work offloaded to `boundedElastic`  
- **Architecture**: Hexagonal (Ports & Adapters)  
- **Persistence**: H2 (dev), JPA adapter (blocking)  
- **Image processing**: [Thumbnailator](https://github.com/coobird/thumbnailator)  
- **Build/Run**: Maven, Docker, Docker Compose  

---

## Design Decisions

### 🏗️ Hexagonal Architecture (Ports & Adapters)


- **Domain**
  - `domain.model` → core entities (e.g., `ImageModel`)
  - `domain.exception` → exceptions (e.g., `NotFoundException`)

- **Application (Ports)**
  - `port.in` → inbound ports (use-cases consumed by web/adapters)
  - `port.out` → outbound ports (e.g., `ImageRepositoryPort`)
  - `service.impl` → implements business logic; wraps blocking ops in `boundedElastic`

- **Infrastructure (Adapters)**
  - **Web adapter**
    - `web.controller` → REST controllers (e.g., `TaskController`)
    - `web.request.dto` / `web.response.dto` → request/response DTOs
    - `web.convert.mapper` → MapStruct DTO mappers
    - `web.advice` → exception handling (`DetailErrorDto`)
  - **Persistence adapter**
    - `persistence.adapter` → implements `ImageRepositoryPort`
    - `persistence.dao` → Spring Data repositories
    - `persistence.entity` → DB entities
    - `persistence.convert.mapper` → MapStruct entity mappers

**Why hexagonal?**
- Clear boundaries, easy to test
- Replaceable adapters (JPA → R2DBC → S3, etc.)
- Web controllers stay thin and focus on mapping

---

## API Endpoints

Base path: `http://localhost:8080/back`

### `GET /task/all`
Returns all image tasks.  

**Response (200)**:
```json
[
  {
    "id": "f2c9d1e3-....",
    "createdAt": "2025-09-11T12:34:56",
    "originalFile": "098f6bcd4621d373cade4e832627b4f6",
    "resolution": "800x600",
    "urlImage": "http://.../files/..."
  }
]
```
---

### `GET /task/{taskId}`

Returns a single task by its unique identifier.

- **200 OK** → task found and returned as `ImageResponseDto`  
- **404 Not Found** → task does not exist  
- **500 Internal Server Error** → unexpected failure (with `DetailErrorDto` payload)  

**Sample response (200)**:
```json
{
  "id": "c4a2f320-8729-4ad9-8895-1f71a0c8e53f",
  "createdAt": "2025-09-11T16:55:41",
  "originalFile": "098f6bcd4621d373cade4e832627b4f6",
  "resolution": "200x100",
  "urlImage": "/repo-app/resized_test.png"
}
```
---

### `POST /task`

Create an image resize task

- **200 OK** → Resized image successfully created. 
- **400 Invalid request** → missing file or incorrect dimensions (unexpected failure (with `DetailErrorDto` payload)
- **500 Internal Server Error** → unexpected failure (with `DetailErrorDto` payload)  

**Sample response (200)**:
```json
{
  Returns the resize image
}
```
---

📚 Tools & Consoles
- Swagger UI → http://localhost:8080/back/swagger-ui/index.html
- H2 Database Console → http://localhost:8080/back/h2-console
  - User: test
  - Password: test


# Steps to follow for execution within a container
- 1.- Download the project.
- 2.- Run Docker Compose with the following command.
  - 2.1.- We specify our default path, as we won't have access to it inside the container. (This is necessary to receive the image once we use the @POST service.)
    - export APP_STORAGE_PATH=/YOU_DESKTOP
  - 2.2.- We run: docker compose up --build -d

# Testing
- Unit tests → run with ./mvnw test
- Reactive flows → verified using reactor-test and StepVerifier
- Controllers → tested with WebTestClient and Mockito (ArgumentMatchers.any, eq, …)

 
## 📦 Postman collectinons:

**Execute in Localhost**
- 1.- Get All Task: http://localhost:8080/back/task/all
- 2.- Get Task by UUID: http://localhost:8080/back/task/{taskId}
- 3.- Post Resize image: http://localhost:8080/back/task

**Execute in VPS**
- 1.- Get All Task: http://200.234.230.76:8080/back/task/all
- 2.- Get Task by UUID: http://200.234.230.76:8080/back/task/{taskId}
- 3.- Post Resize image: http://200.234.230.76:8080/back/task

## 🚨 Troubleshooting

413 Request Entity Too Large
Increase multipart or codec limits in application.properties.

400 Bad Request (multipart)
Ensure Content-Type: multipart/form-data and that the file field is uploaded as File.

## 📂 Project Structure:

src/main/java/com/now/app/be/main
├── application.port.in

├── application.port.out

├── application.port.service.impl

├── domain.exception
├── domain.model
├── infrastructure.persistence.adapter
├── infrastructure.persistence.convert.mapper
├── infrastructure.persistence.dao
├── infrastructure.persistence.entity
├── infrastructure.web.advice
├── infrastructure.web.controller
├── infrastructure.web.convert.mapper
├── infrastructure.web.request.dto
└── infrastructure.web.response.dto







