# AccountManagment

## Purpose
Account management microservice. Currently a stub with basic Eureka registration and a status check endpoint.

## Configuration
| Property | Value |
|----------|-------|
| Port | Dynamic (`0` — assigned by OS) |
| Spring Boot | `3.4.3` |
| Spring Cloud | `2024.0.0` |
| Java | `17` |
| Eureka | `http://localhost:8091/eureka` |

## Run
```bash
mvn spring-boot:run
```

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/account/status/check` | Health check |

## Architecture
- **Controller**: `AccountManagementController`

## Dependencies
- **Infrastructure**: MicroLearnDiscovery (Eureka), APIGateway (routing at `/account/**`)

## Testing
| Aspect | Value |
|--------|-------|
| **JUnit** | JUnit 5 (Jupiter) |
| **Assertions** | AssertJ |
| **Mocking** | Mockito |
| **Swagger UI** | `http://localhost:<port>/swagger-ui.html` |

## Roadmap
- [ ] Implement account CRUD operations
- [ ] Add JPA entity and repository layer
- [ ] Add database (currently no persistence)
- [ ] Add security/authorization
- [ ] Add validation and error handling
