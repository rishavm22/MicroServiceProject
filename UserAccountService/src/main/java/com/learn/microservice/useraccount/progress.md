# Module: com.learn.microservice.useraccount

## Design Doc
Root package for UserAccountService — the core user management and authentication microservice. Contains:
- **Entry point**: `UserAccountServiceApplication.java`
- **Constants**: `AppConstants.java` (JWT token expiry, auth header keys)
- **Config**: Security, JWT, application properties, data initialization
- **Controllers**: `AuthController` (registration, login), `UsersController` (user CRUD)
- **Services**: `UserService`, `UserServiceImpl`, `AuthService`
- **Data**: JPA `User` entity, `UserRepository`, `Role` enum
- **DTOs**: Request/response DTOs for registration, login, user updates
- **Exceptions**: Custom exception classes and structured error responses

## Dependencies (Internal)
- `config/` → Security config, JWT utilities, application properties
- `controller/` → REST endpoints
- `services/` → Business logic layer
- `entities/` → JPA entities
- `repository/` → Spring Data JPA repos
- `enums/` → Role constants
- `exceptions/` → Error handling framework

## Status
### Implemented
- [x] Spring Boot 3.3.6 with Spring Security (stateless JWT)
- [x] User registration with validation (`/users/auth/reg`)
- [x] Login with JWT token generation (`/users/auth/signIn`)
- [x] User CRUD (`/users/all`, `/users/update/{id}`)
- [x] H2 in-memory DB with Spring Data JPA
- [x] BCrypt password encoding
- [x] Custom `AuthenticationFilter` for JWT interception
- [x] Global exception handling with structured error responses
- [x] Gateway IP whitelist for `/users/**`
- [x] Forgot-password scaffold (not yet sending email)

### Roadmap
- [ ] Implement forgot-password (email sending)
- [ ] Add refresh token endpoint
- [ ] Add user deletion
- [ ] Replace H2 with production DB (PostgreSQL/MySQL)
- [ ] Add pagination for user list endpoint
- [ ] Add role-based access control
- [ ] Add audit logging

### Test Coverage Status
- [ ] Service layer — `UserServiceImpl` (CRUD, password validation)
- [ ] Auth service — `AuthService` (login, token generation)
- [ ] Controllers — `AuthController`, `UsersController` (validation, status codes)
- [ ] Security — `AuthenticationFilter`, `WebSecurity` (gateway IP restriction)
- [ ] Exception handler — `AppExceptionHandler` (structured responses)
- [ ] Repository — `UserRepository` (findByEmail, custom queries)
- [ ] Test data — Create `testutil/UserDTOBuilder` and `UserTestData` object mother
