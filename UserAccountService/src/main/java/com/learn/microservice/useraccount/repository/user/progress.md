# Package: repository/user/

## Design Doc
Spring Data JPA repositories for user entities. `UserRepository` provides standard CRUD plus custom query methods (findByEmail, etc.).

## Dependencies
- `../../entities/user/User` — Managed entity
- `../../config/security/CustomUserDetailService` — Uses repo for user lookup

## Status
### Implemented
- [x] `UserRepository` with standard Spring Data methods
- [x] Custom query methods for authentication lookups

### Test Coverage Status
- [ ] `@DataJpaTest` — save, findById, findByEmail, findAll
- [ ] Unique constraint enforcement (duplicate email)
