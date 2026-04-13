# Package: config/

## Design Doc
Top-level configuration package. Contains `ApplicationConfig` (general Spring config) and sub-packages for security, web, and JWT concerns.

## Dependencies
- `security/` — Spring Security, JWT, auth filter
- `web/` — `ApplicationProperties` (typed config from `application.properties`)

## Status
### Implemented
- [x] `ApplicationConfig` — Bean configuration (ModelMapper)

### Roadmap
- [ ] Add Swagger/OpenAPI config
- [ ] Add CORS configuration
