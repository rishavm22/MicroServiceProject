# Package: services/user/

## Design Doc
Business logic layer for user management. Contains the `UserService` interface and `UserServiceImpl`, plus sub-packages for authentication (`auth/`), DTOs (`dto/`, `registration/dto/`, `login/dto/`).

## Dependencies
- `../../repository/user/UserRepository` — Data access
- `../../entities/user/User` — Domain entity
- `auth/` — Authentication service
- `dto/` — Data transfer objects

## Status
### Implemented
- [x] `UserService` interface with `UserServiceImpl`
- [x] User creation with password encoding
- [x] User retrieval and update
- [x] DTO-based API (ModelMapper mapping)

### Roadmap
- [ ] Add user deletion
- [ ] Add pagination support
