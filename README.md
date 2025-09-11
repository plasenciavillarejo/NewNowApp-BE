NewNowApp-BE — Image Task API

A technical test backend that receives an image, resizes it to requested dimensions, persists task metadata, and (optionally) stores the processed image on disk.

Language / Platform: Java 17, Spring Boot

Style: Reactive controller signatures (Mono<>) with blocking work offloaded to boundedElastic

Architecture: Hexagonal (Ports & Adapters)

Persistence: H2 (dev), JPA adapter (blocking)

Image processing: Thumbnailator

Build/Run: Maven, Docker, Docker Compose

1) Design decisions
Hexagonal Architecture (Ports & Adapters)

The codebase follows a clear separation of concerns so domain logic stays independent from delivery and persistence:

Domain

com.now.app.be.main.domain.model → core entities (e.g., ImageModel)

com.now.app.be.main.domain.exception → domain exceptions (e.g., NotFoundException)

Application (Ports)

application.port.in → inbound ports (use-cases consumed by web/adapters)

application.port.out → outbound ports (required by the domain to access infra)
Example: ImageRepositoryPort (find/save images)

application.port.service.impl → application service(s) implementing inbound ports and orchestrating outbound ports; returns reactive types (Mono<>) while wrapping blocking I/O in boundedElastic.

Infrastructure (Adapters)

Web adapter

infrastructure.web.controller → REST controllers (e.g., TaskController)

infrastructure.web.request.dto / response.dto → DTOs for request/response

infrastructure.web.convert.mapper → MapStruct DTO mappers

infrastructure.web.advice → global exception handling (DetailErrorDto)

Persistence adapter

infrastructure.persistence.adapter → implements ImageRepositoryPort using ImageDao

infrastructure.persistence.dao → Spring Data repository (JPA)

infrastructure.persistence.entity → DB entities

infrastructure.persistence.convert.mapper → MapStruct domain↔entity mappers

Why hexagonal?

Clear boundaries, easy to test.

You can swap persistence (JPA → R2DBC → S3, etc.) without touching domain.

Web layer stays thin (controllers map DTOs ↔ domain and delegate to services).

Reactive controller signatures with controlled blocking

Upload endpoints are exposed as reactive (Mono<ResponseEntity<...>>).

Image processing (Thumbnailator), file system I/O and JPA are blocking by nature, so the service wraps them inside:
