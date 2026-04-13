# Package: config/web/

## Design Doc
Web configuration package. Contains `ApplicationProperties` — a typed wrapper that reads JWT keys from `application.properties` (`app.jwt.key`, `app.jwt.refreshToken`).

## Dependencies
- `application.properties` / `application-local.properties` — Externalized config source

## Status
### Implemented
- [x] `@ConfigurationProperties`-based JWT key binding
- [x] Default value handling for missing properties

### Roadmap
- [ ] Add validation annotations (`@NotBlank`) on key fields
- [ ] Move to `@ConfigurationPropertiesScan` pattern
