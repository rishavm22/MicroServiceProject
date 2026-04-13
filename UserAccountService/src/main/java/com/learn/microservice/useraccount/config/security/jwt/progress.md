# Package: config/security/jwt/

## Design Doc
JWT token generation, validation, and extraction utilities. Signs access tokens with HS256 and refresh tokens with HS512.

## Dependencies
- `../../config/web/ApplicationProperties` — Base64-encoded signing keys
- `../../enums/Role` — Embedded in JWT claims
- `../../AppConstants` — Token expiry constants

## Status
### Implemented
- [x] Access token generation (HS256, 1hr expiry)
- [x] Refresh token generation (HS512, 12hr expiry)
- [x] Token validation (expiry + subject check)
- [x] Username extraction from token claims

### Roadmap
- [ ] Add token blacklisting/revocation
- [ ] Add JTI (JWT ID) claim for refresh tracking
- [ ] Support key rotation

### Test Coverage Status
- [ ] Token generation — access + refresh tokens are valid JWTs
- [ ] Token extraction — extract username, check expiry
- [ ] Token validation — expired + valid tokens
