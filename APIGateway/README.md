# APIGateway

## Purpose
Spring Cloud Gateway (WebFlux-based) acting as the single entry point for all client requests. Routes are resolved via Eureka service discovery with JWT token validation.

## Configuration
| Property | Value |
|----------|-------|
| Port | `8082` |
| Spring Boot | `3.4.3` |
| Spring Cloud | `2024.0.0` |
| Eureka Discovery | `http://localhost:8091/eureka` |
| JWT Validation | `AuthorizationHeaderFilter` (HS256) |

## Run
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Required Local Configuration
Create `src/main/resources/application-local.properties`:
```properties
jwt.secret=your-jwt-secret
```

Or set in IntelliJ Run Configuration:
- `JWT_SECRET`

## Routes

| Route | Method | Target | Auth |
|-------|--------|--------|------|
| `/users/status/check` | GET | `lb://user-ws` | No |
| `/users/auth/**` | GET, POST | `lb://user-ws` | No |
| `/user/login` | POST | `lb://user-ws` | No |
| `/users/**` | GET, PUT, DELETE | `lb://user-ws` | Bearer token |
| `/account/**` | GET, POST | `lb://account-management` | No |
| `/docs/**` | any | `lb://doc-manager-ws` | No |
| `/h2-console/**` | GET | `lb://user-ws` | No |

## Architecture
- `ApigatewayApplication` — Entry point
- `AuthorizationHeaderFilter` — Custom gateway filter factory for JWT validation
- `MyPreFilter` — Global pre-filter (logging)
- `MyPostFilter` — Global post-filter
- `GlobalFilterConfiguration` — Global filter wiring

## Testing
| Aspect | Value |
|--------|-------|
| **JUnit** | JUnit 5 (Jupiter) |
| **Assertions** | `StepVerifier` (reactor-test) |
| **Mocking** | Spring `WebTestClient` |
| **Integration** | `WebTestClient` for reactive route testing |
