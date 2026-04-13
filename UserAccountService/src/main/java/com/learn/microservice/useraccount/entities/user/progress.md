# Package: entities/user/

## Design Doc
JPA entity definitions for the user domain. `User` maps to the `users` table, implements `UserDetails` for Spring Security integration.

## Dependencies
- `../../repository/user/UserRepository` — JPA repository for this entity
- `../../enums/Role` — User roles embedded in the entity

## Status
### Implemented
- [x] `User` entity with Spring Security `UserDetails` implementation
- [x] BCrypt-encrypted password field
- [x] Role-based access via `Role` enum

### Roadmap
- [ ] Add audit fields (`@CreatedDate`, `@LastModifiedDate`)
- [ ] Add soft-delete support
