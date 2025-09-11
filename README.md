# NewNowApp-BE — Image Task API

A technical test backend that receives an image, resizes it to requested dimensions, persists task metadata, and (optionally) stores the processed image on disk.

---

## Overview

- **Language / Platform**: Java 17, Spring Boot  
- **Style**: Reactive controllers (`Mono<>`) with blocking work offloaded to `boundedElastic`  
- **Architecture**: Hexagonal (Ports & Adapters)  
- **Persistence**: H2 (dev), JPA adapter (blocking)  
- **Image processing**: [Thumbnailator](https://github.com/coobird/thumbnailator)  
- **Build/Run**: Maven, Docker, Docker Compose  

---

## Design Decisions

### Hexagonal Architecture (Ports & Adapters)

