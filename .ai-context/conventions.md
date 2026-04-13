# Conventions — Global Rulebook

> This document is the authoritative reference for coding standards, architectural patterns, and technology versions across all microservices in this project. Always follow these rules.

---

## 1. Tech Stack

| Component | Version(s) | Notes |
|---|---|---|
| **Java** | 17 (UserAccountService, APIGateway, MicroConfigServer, MicroLearnDiscovery) / 11 (doc-manager) | doc-manager is the outlier — still on Boot 2.7.13 |
| **Spring Boot** | 3.4.5 (Config), 3.4.3 (Gateway), 3.3.6 (Discovery, UserService), 2.7.13 (doc-manager) | |
| **Spring Cloud** | 2024.0.1 (Config), 2024.0.0 (Gateway), 2023.0.3 (Discovery, UserService), 2021.0.3 (doc-manager) | |
| **Build** | Maven (mvnw in all modules) | |
| **Lombok** | Via Spring Boot BOM (UserService, Gateway) | Commented out in doc-manager |
| **Database** | H2 in-memory (UserService) | `jdbc:h2:mem:testdb` |
| **ORM** | Spring Data JPA (UserService) | |
| **Security** | Spring Security + JWT (`jjwt` 0.11.5 in UserService, `jjwt` 0.12.6 in Gateway) | Stateful JWT, stateless sessions |
| **API Gateway** | Spring Cloud Gateway (WebFlux-based) | Reactive filter chain |
| **Service Discovery** | Netflix Eureka Server/Client | Port 8091 |
| **Config Server** | Spring Cloud Config Server (Git-backed) | Port 8012 |
| **Document Processing** | Apache POI 4.1.2, FOP 2.9, docx4j 11.5.0 | doc-manager only |
| **JAXB / XML** | `javax.xml.bind:jaxb-api:2.3.1` (UserService), `jakarta.xml.bind-api:3.0.1` + `jaxb-runtime:3.0.2` (doc-manager) | Mismatched namespaces — Boot 2 vs 3 transition gap |
| **DTO Mapping** | ModelMapper 3.2.2 (UserService), Jackson XML (`jackson-dataformat-xml`) | |
| **Validation** | `spring-boot-starter-validation` 3.4.2 (UserService) | `@Valid` on controller inputs |
| **Dev Tools** | `spring-boot-devtools` (UserService, doc-manager, Gateway) | Hot restart enabled |
| **Testing** | `spring-boot-starter-test` + `reactor-test` (Gateway) | |

---

## 2. Architecture Patterns

### 2.1 Microservices Communication
- **Discovery-first routing**: All services register with Eureka on `http://localhost:8091/eureka`
- **Gateway-based routing**: Spring Cloud Gateway uses `lb://<service-name>` for load-balanced lookups via Eureka
- **Centralized config**: MicroConfigServer serves configs from a GitHub repository

### 2.2 Security Architecture
- **JWT-based authentication**:
  - `UserAccountService` generates JWT tokens (HS256 for access, HS512 for refresh) on `/users/auth/signIn`
  - `APIGateway` validates tokens via a custom `AuthorizationHeaderFilter` (Gateway custom filter) on protected routes
  - Token expiry: 24 hours (86400000ms)
  - Gateway IP whitelist (`GATEWAY_IP`) restricts `/users/**` at the service layer
- **Spring Security**: Stateless session, method-level security (`@EnableMethodSecurity`)
- **Custom authentication filter**: `AuthenticationFilter` intercepts requests before `UsernamePasswordAuthenticationFilter`

### 2.3 Data Layer
- **JPA Repository pattern**: `UserRepository` extends Spring Data interfaces
- **In-memory H2**: For development/testing — no production DB configured yet
- **Entity-DTO separation**: Strict separation via ModelMapper; DTOs named with `*DTO` suffix

### 2.4 Gateway Filters
- **Pre/Post filters**: `MyPreFilter`, `MyPostFilter` for cross-cutting concerns
- **Custom filter factory**: `AuthorizationHeaderFilter` validates JWTs
- **Global filter config**: `GlobalFilterConfiguration` for global interceptors

### 2.5 Document Processing (doc-manager)
- **DOCX template → PDF pipeline**: Upload `.docx` template, populate placeholders, convert to PDF
- **Apache FOP**: For PDF rendering
- **docx4j**: For DOCX parsing and placeholder replacement
- **Custom namespace mapper**: `CustomNamespacePrefixMapper` + `DocxPlaceholderReplacer`

### 2.6 Exception Handling
- **Global exception handler**: `AppExceptionHandler` (`@ControllerAdvice` equivalent)
- **Error code enums**: `ErrorCode`, `GlobalErrorCode` for structured error responses
- **Custom exceptions**: `UsernameException`, `ValidationException`, `BaseRuntimeException`

### 2.7 Testing
- **Unit tests**: Spring Boot test scaffolds in each module (placeholder `*Tests.java` classes)
- **Reactive test**: `reactor-test` for Gateway WebFlux tests
- **Test data**: No dedicated test data factory pattern — use inline fixtures

---

## 3. Naming Conventions

- **Application classes**: `*Application.java` (entry points)
- **DTOs**: `*DTO` suffix (e.g., `CreateUserRequestDTO`, `UserResponseDTO`)
- **Services**: `*Service` interfaces with `*ServiceImpl` implementations
- **Controllers**: `*Controller.java`
- **Filters**: Direct class names (e.g., `AuthorizationHeaderFilter`, `MyPreFilter`)
- **Exceptions**: `*Exception.java` at root, error codes in `error/` subpackage
- **Properties**: `ApplicationProperties.java` for typed externalized config

---

## 4. Configuration Rules

- **Secrets**: Never commit secrets. Use `application-local.properties` (gitignored) for local dev.
- **Profile activation**: Run with `--spring.profiles.active=local` to load local secrets.
- **Port assignment**: Services use `server.port=0` for dynamic allocation, except `doc-manager` (8087) and infrastructure services (discovery 8091, config 8012, gateway 8082).
- **Eureka config**: Discovery at `http://localhost:8091/eureka`. All microservices must reference this.
- **Environment variables**: Prefix sensitive values with `${VAR_NAME:}` for fallback support.

---

## 5. Code Quality Rules

- **No hardcoded credentials** in `application.properties` or code files
- **Stateless auth**: No HTTP sessions — JWT in `Authorization: Bearer` header
- **Layered architecture**: Controller → Service → Repository (no direct DB access from controllers)
- **DTO projection**: Never expose JPA entities directly to clients
- **Validation**: Use `@Valid` + `spring-boot-starter-validation` on request bodies
- **Lombok**: Use `@Data`, `@RequiredArgsConstructor`, `@Slf4j` consistently (except doc-manager)

## 6. Safety Rules (Non-Negotiable)

- **No deletion**: Never delete a file or directory without confirming with the user.
- **No force-push**: Never run `git push --force` or `git reset --hard` against shared branches.
- **No secret commits**: Never re-introduce credentials into files tracked by git.
- **No silent infrastructure changes**: Never modify `.run/` configurations or CI pipelines without asking.
- **No destructive git operations**: Never use `--no-verify`, `--force`, or bypass hooks without explicit instruction.
