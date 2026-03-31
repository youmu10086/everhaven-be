# Everhaven Project Skill Guide

This document defines the "Skill" (context and instructions) for AI assistants to better understand and work with the Everhaven Spring Boot backend project.

## Project Overview
- **Name**: Everhaven Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Purpose**: A backend system for a housing/apartment management platform (Everhaven).
- **Core Features**: Navigation management, Carousel management, Image synchronization, User authentication (JWT/RSA).

## Technical Stack
- **Web**: Spring Web (REST Controllers)
- **Database**: MyBatis (indicated by `@MapperScan` and file structure)
- **Security**: JWT for tokens, RSA for encryption, MD5 for hashing.
- **Documentation**: SpringDoc OpenAPI (Swagger).
- **Utilities**: Lombok for Boilerplate, Hutool (implied by file/image handling patterns).

## Architecture & Design Patterns
### Layering
- `controller`: REST API endpoints. Uses `Result<T>` for unified responses.
- `service`: Business logic interfaces.
- `service.impl`: Implementation of business logic.
- `mapper`: Data access layer (MyBatis).
- `model.entity`: Database table mappings.
- `model.vo`: View Objects for API responses (e.g., `NavCategoryVO`, `NavItemVO`).
- `model.dto`: Data Transfer Objects for API requests.
- `common`: Common utility classes like `Result`.

### Coding Standards
- **Unified Response**: Every API should return `com.cyf.everhavenbe.common.Result<T>`.
- **Lombok**: Use `@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`, and `@Builder` to reduce boilerplate.
- **Dependency Injection**: Prefer constructor injection over field injection (`@Autowired`).
- **Swagger**: Use `@Tag` and `@Operation` for documenting controllers and endpoints.

### File Handling
- Local image storage is used (paths like `/uploads/images/...`).
- Controllers handles image sync and carousel slide management.

## AI Instructions (The "Skill")
When generating code or providing suggestions for this project, the AI should:

1.  **Strict Layering**: Ensure new logic is placed in the correct layer (Entity -> Mapper -> Service -> Controller).
2.  **DTO/VO Usage**: Never return Entities directly to the frontend. Always map them to VO classes.
3.  **Result Wrapper**: Wrap all controller return types in `Result.success(data)` or `Result.error(message)`.
4.  **Java 17+ Features**: Use modern Java features like `Optional`, Streams, and records where appropriate.
5.  **Logging**: Use `@Slf4j` for logging within services and controllers.
6.  **Exception Handling**: Use the project's custom exception handling mechanism (look at `exception/` package) instead of returning raw error codes.
7.  **Resource Management**: Ensure proper path handling for file uploads using relative paths and configurable storage locations.

---
*Created on 2026-03-31 to optimize AI outputs for Everhaven.*

