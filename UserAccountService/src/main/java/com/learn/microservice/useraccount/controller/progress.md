# Package: com.learn.microservice.useraccount.controller

## Design Doc
REST controllers exposing user authentication and management endpoints.

## Dependencies
- `../services/user/UserService` — user CRUD
- `../services/user/auth/AuthService` — login/registration
- DTOs from `../services/user/dto/`, `../services/user/auth/`, `../services/user/registration/dto/`

## Status
### Implemented
- [x] `AuthController` — `/users/auth/**`: registration, login, forgot-password
- [x] `UsersController` — `/users/**`: status check, list all, update

### Roadmap
- [ ] Add pagination to user listing
- [ ] Add user delete endpoint
- [ ] Add password change endpoint

### Test Coverage Status
- [ ] `AuthController` — POST /reg (valid/invalid input), POST /signIn, POST /forgot-password
- [ ] `UsersController` — GET /all, PUT /update/{id} (user found / not found)
- [ ] Exception handling —  @Valid failure responses, auth failures
