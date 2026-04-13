# MicroServiceProject

A Spring Boot microservices ecosystem with service discovery, centralized configuration, API gateway routing, and JWT-based authentication. The system manages user authentication, account management, and document (DOCX → PDF) generation.

## Architecture

```
Client → APIGateway (8082) → MicroLearnDiscovery (8091 - Eureka)
                              ├── UserAccountService     (user-ws)
                              ├── AccountManagment       (account-management)
                              ├── doc-manager            (doc-manager-ws)
                              └── MicroConfigServer      (8012 - Config)
```

| Service | Port | Description |
|---------|------|-------------|
| MicroLearnDiscovery | `8091` | Eureka Service Registry |
| MicroConfigServer | `8012` | Spring Cloud Config Server (GitHub-backed) |
| APIGateway | `8082` | Spring Cloud Gateway (JWT-validated) |
| UserAccountService | dynamic (`0`) | User CRUD, registration, login (JWT), H2 DB |
| AccountManagment | dynamic (`0`) | Account management |
| doc-manager | `8087` | DOCX template → PDF generation |

## Prerequisites

- Java 17 (Java 11 for `doc-manager`)
- Maven

## Project Brief

The platform provides centralized user account management and document generation capabilities. Users can register, authenticate via JWT, manage their profiles, and generate PDF documents from DOCX templates. An API Gateway routes all client requests to the appropriate microservice, while Eureka handles service discovery and the Config Server provides centralized configuration from a GitHub repository.

## Tech Stack

| Component | Version | Used In |
|-----------|---------|---------|
| **JDK** | 17 | UserAccountService, APIGateway, MicroConfigServer, MicroLearnDiscovery |
| **JDK** | 11 | doc-manager |
| **Spring Boot** | 3.4.5 | MicroConfigServer |
| **Spring Boot** | 3.4.3 | APIGateway |
| **Spring Boot** | 3.3.6 | MicroLearnDiscovery, UserAccountService |
| **Spring Boot** | 2.7.13 | doc-manager |
| **Spring Cloud** | 2024.0.1 | MicroConfigServer |
| **Spring Cloud** | 2024.0.0 | APIGateway |
| **Spring Cloud** | 2023.0.3 | MicroLearnDiscovery, UserAccountService |
| **Spring Cloud** | 2021.0.3 | doc-manager |
| **Maven** | 3.x (mvnw) | All modules |
| **Spring Security** | via Boot BOM | UserAccountService, APIGateway |
| **Spring Data JPA** | via Boot BOM | UserAccountService |
| **H2 Database** | via Boot BOM | UserAccountService |
| **jjwt** | 0.11.5 | UserAccountService |
| **jjwt** | 0.12.6 | APIGateway |
| **Lombok** | via Boot BOM | UserAccountService, APIGateway |
| **ModelMapper** | 3.2.2 | UserAccountService |
| **Validation** | 3.4.2 | UserAccountService |
| **Apache POI** | 4.1.2 | doc-manager |
| **Apache FOP** | 2.9 | doc-manager |
| **docx4j** | 11.5.0 | doc-manager |
| **JAXB (Jakarta)** | 3.0.1 / 3.0.2 | doc-manager |
| **JAXB (javax)** | 2.3.1 | UserAccountService |
| **Spring Cloud Gateway** | 4.1.5 | APIGateway |
| **Project Lombok** | via Boot BOM | UserAccountService, APIGateway |

## Setup & Run

### 1. Start the Service Discovery (Eureka)

```bash
cd MicroLearnDiscovery
mvn spring-boot:run
```

### 2. Start the Config Server

```bash
cd MicroConfigServer
mvn spring-boot:run
```

### 3. Start the API Gateway

```bash
cd APIGateway
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### 4. Start Microservices

```bash
cd UserAccountService
mvn spring-boot:run -Dspring-boot.run.profiles=local

cd doc-manager
mvn spring-boot:run

cd AccountManagment
mvn spring-boot:run
```

## Local Development with Secrets

Sensitive configuration (JWT keys, gateway IPs, Git credentials) is split into `application-local.properties` files that are **gitignored**.

### Setup

Create `application-local.properties` in each service's `src/main/resources/` directory:

**Gateway:**
```properties
# APIGateway/src/main/resources/application-local.properties
jwt.secret=your-jwt-secret
```

**UserAccountService:**
```properties
# UserAccountService/src/main/resources/application-local.properties
gateway.ip=10.0.0.113
app.jwt.key=your-base64-jwt-key
app.jwt.refreshToken=your-refresh-token
```

**MicroConfigServer:**
```properties
# MicroConfigServer/src/main/resources/application-local.properties
spring.cloud.config.server.git.username=your-github-username
spring.cloud.config.server.git.password=your-github-pat
```

> Check `.env.example` for the full list of required variables.

See `TODO.md` for security items and pending work.

## API Gateway Routes

| Route | Method | Target Service | Auth Required |
|-------|--------|----------------|---------------|
| `/users/status/check` | GET | user-ws | No |
| `/users/auth/**` | GET, POST | user-ws | No |
| `/user/login` | POST | user-ws | No |
| `/users/**` | GET, PUT, DELETE | user-ws | Yes (Bearer token) |
| `/account/**` | GET, POST | account-management | No |
| `/docs/**` | any | doc-manager-ws | No |
| `/h2-console/**` | GET | user-ws | No |

## Security

The project uses JWT-based auth:
- `UserAccountService` generates tokens on login
- `APIGateway` validates tokens on protected routes via `AuthorizationHeaderFilter`
- User service restricts `/users/**` to gateway IP only (when `gateway.ip` is configured)

> The GitHub PAT previously committed to the config server has been moved to local properties. **Rotate the leaked token** — see `TODO.md`.

## API Specification

An OpenAPI 3.0 spec is maintained at `api-spec/openapi.yaml`. Each service also exposes Swagger UI at runtime:

| Service | Swagger UI URL |
|---------|---------------|
| UserAccountService | `http://localhost:<port>/swagger-ui.html` |
| AccountManagment | `http://localhost:<port>/swagger-ui.html` |
| doc-manager | `http://localhost:8087/swagger-ui.html` |

The canonical spec file (`api-spec/openapi.yaml`) should be updated whenever new endpoints are added or existing ones change.
