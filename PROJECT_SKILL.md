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
- `exception`: Global exception handling logic.

### Coding Standards
- **Unified Response**: Every API should return `com.cyf.everhavenbe.common.Result<T>`.
    - `Result.success(data)` / `Result.success()`
    - `Result.error(message)` / `Result.error(code, message)`
- **Swagger/OpenAPI (Required for APIFox)**: 
    - Every Controller class **must** have a `@Tag` annotation with a name and description (e.g., `@Tag(name = "User Management", description = "用户管理相关操作")`).
    - Every public API method **must** have an `@Operation(summary = "...", description = "...")` annotation.
    - **Note**: The `description` and `summary` fields **must** be in **Chinese** to ensure better readability in APIFox.
- **Lombok**: Use `@Data`, `@AllArgsConstructor`, `@NoArgsConstructor`, and `@Builder` to reduce boilerplate.
- **Dependency Injection**: Prefer constructor injection over field injection (`@Autowired`).
- **Validation**: Use `jakarta.validation` annotations (e.g., `@NotBlank`, `@Pattern`). The `GlobalExceptionHandler` handles `ConstraintViolationException` and `HandlerMethodValidationException`.
- **Security**: 
    - Use `RSAUtil` for decrypting sensitive information.
    - Use `JwtUtil` for token generation and verification.
    - Token claims should be mapped to the `claims` key (Map<String, Object>).

### AI Instructions (The "Skill")
When generating code or providing suggestions for this project, the AI should:

1.  **Strict Layering and Model Creation**: 
    - When adding or modifying a business model, ensure **all three layers** are created:
        - **Entity**: Located in `model.entity`, representing the database table structure. Use JPA/MyBatis annotations as appropriate.
        - **DTO (Data Transfer Object)**: Located in `model.dto`, used for receiving request data from the frontend (e.g., in `POST` or `PUT` methods). Use validation annotations if necessary.
        - **VO (View Object)**: Located in `model.vo`, used for returning data to the frontend. Ensure internal fields (like image URLs) are properly processed or localized before returning.
2.  **DTO/VO Usage**: Never return Entities directly to the frontend. Always map them to VO classes located in `model.vo`.
3.  **Result Wrapper**: Wrap all controller return types in `Result<T>`.
4.  **Logging**: Use `@Slf4j` (or `LoggerFactory.getLogger` if consistent with existing files) for logging.
5.  **Validation**: Apply validation on DTO fields and handle them via the global handler.
6.  **Naming Conventions**:
    - Table columns map to camelCase in Java.
    - Interfaces in `service` package, implementations in `service.impl`.
    - Mapper files should be in the `mapper` package.
7.  **Modern Java**: Use Java 17+ features like `List.of()`, `Optional`, and Streams.

---
*Created on 2026-03-31 to optimize AI outputs for Everhaven.*
