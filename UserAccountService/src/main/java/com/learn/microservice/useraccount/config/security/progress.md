# Package: config/security/

## Design Doc
Spring Security configuration package. Defines the complete auth chain: `WebSecurity` (filter chain), `AuthenticationFilter` (JWT request interception), `CustomUserDetailService` (user lookup), and JWT utilities.

## Dependencies
- `jwt/` — Token generation and validation
- `../../services/user/UserService` — User lookup for auth
- `../../repository/user/UserRepository` — DB access via CustomUserDetailService
- `../../config/web/ApplicationProperties` — JWT key and refresh token values

## Status
### Implemented
- [x] Stateless Spring Security filter chain
- [x] Custom `AuthenticationFilter` for JWT extraction
- [x] BCrypt-based password encoding
- [x] Gateway IP restriction for `/users/**`
- [x] `@EnableMethodSecurity` for method-level auth

### Roadmap
- [ ] Add method-level role annotations (`@PreAuthorize`)
- [ ] Consolidate duplicate permission rules

### Test Coverage Status
- [ ] `AuthenticationFilter` — valid token, expired token, malformed token, missing header
- [ ] `WebSecurity` — gateway IP restriction active when configured
- [ ] `CustomUserDetailService` — user found / not found
