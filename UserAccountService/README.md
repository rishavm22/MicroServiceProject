# UserAccountService

## Purpose
Core user management and authentication microservice. Handles user registration, login (JWT-based), profile CRUD, and forgot-password workflows.

## Configuration
| Property | Value |
|----------|-------|
| Port | Dynamic (`0` — assigned by OS) |
| Spring Boot | `3.3.6` |
| Spring Cloud | `2023.0.3` |
| Database | H2 in-memory (`jdbc:h2:mem:testdb`) |
| H2 Console | `http://localhost:<port>/h2-console` |
| Eureka | `http://localhost:8091/eureka` |
| JWT | HS256 access token (1hr), HS512 refresh token (12hr) |

## Run
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Required Local Configuration
Create `src/main/resources/application-local.properties`:
```properties
gateway.ip=10.0.0.113
app.jwt.key=c3BvcnRzaXhqdW1wZ29sZGVuaXRzaGVsZmNyZWFtdGVtcGVyYXR1cmVob3NwaXRhbHI=
app.jwt.refreshToken=your-refresh-token
```

## Endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/users/status/check` | No | Health check |
| POST | `/user/login` | No | User login (JWT generation) |
| POST | `/users/auth/reg` | No | User registration |
| POST | `/users/auth/signIn` | No | User sign-in |
| POST | `/users/auth/forgot-password` | No | Forgot password |
| GET | `/users/all` | Gateway only | List all users |
| PUT | `/users/update/{id}` | Gateway only | Update user |

## Architecture
- **Controllers**: `AuthController`, `UsersController`
- **Services**: `AuthService`, `UserServiceImpl`
- **Security**: `WebSecurity`, `AuthenticationFilter`, `JWTService`, `CustomUserDetailService`
- **Data**: `User` (JPA entity, `UserRepository`
- **DTOs**: `CreateUserRequestDTO`, `UserResponseDTO`, `UserDTO`, `AuthRequestDTO`, `AuthResponseDTO`
- **Exceptions**: `AppExceptionHandler`, `UsernameException`, `ValidationException`, `BaseRuntimeException`

## Dependencies
- **Infrastructure**: MicroLearnDiscovery (Eureka), MicroConfigServer (Config), APIGateway (routing)
- **Libraries**: Spring Security, jjwt 0.11.5, H2, ModelMapper 3.2.2, Spring Data JPA, Lombok

## Testing
| Aspect | Value |
|--------|-------|
| **JUnit** | JUnit 5 (Jupiter) |
| **Assertions** | AssertJ |
| **Mocking** | Mockito |
| **Integration** | `@SpringBootTest` + `@AutoConfigureMockMvc` |
| **Database** | H2 in-memory (`@DataJpaTest`) |
| **Test Data Pattern** | Object Mother + Fluent Builder (`testutil/`) |
| **Swagger UI** | `http://localhost:<port>/swagger-ui.html` |
