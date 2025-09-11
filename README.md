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

**Swagger URL:** `http://localhost:8080/back/swagger-ui/index.html`

# Steps to follow for execution within a container
- Download the project.
- Run Docker Compose with the following command.
  - We specify our default path, as we won't have access to it inside the container. (This is necessary to receive the image once we use the @POST service.)
    - export APP_STORAGE_PATH=/YOU_DESKTOP







