# Package: services/user/registration/dto/

## Design Doc
Registration DTOs. `CreateUserRequestDTO` (request body for registration) and `UserResponseDTO` (response after successful creation).

## Dependencies
- `../../UserService` — Creates users from `CreateUserRequestDTO`, returns `UserResponseDTO`
- `../../AuthController` — Consumes both in registration endpoint

## Status
### Implemented
- [x] `CreateUserRequestDTO` with Bean Validation annotations
- [x] `UserResponseDTO` for registration response
