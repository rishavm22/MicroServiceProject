# Package: services/user/auth/

## Design Doc
Authentication business logic. `AuthService` handles sign-in (Spring `AuthenticationManager` + JWT token generation) and forgot-password flow (scaffold).

## Dependencies
- `../UserService` — User lookup
- `../../config/security/jwt/JWTService` — Token generation
- `../../config/security/CustomUserDetailService` — User details loading
- `../../repository/user/UserRepository` — Direct DB access for auth checks

## Status
### Implemented
- [x] JWT-based login via `AuthenticationManager`
- [x] Forgot-password scaffold (TODO: email integration)

### Roadmap
- [ ] Implement forgot-password (email sending with reset link)
- [ ] Add refresh token rotation

### Test Coverage Status
- [ ] `AuthService.signinUser` — valid credentials, invalid credentials
- [ ] `AuthService.forgotPassword` — email flow (when implemented)
