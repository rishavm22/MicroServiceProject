# APIGateway Module

## Design Doc
Spring Cloud Gateway (WebFlux-based) acting as the single entry point for all client requests. Routes are configured via `application.properties` using Eureka service discovery (`lb://` URIs). Provides three layers of filtering:
- **Global Pre-filter** (`MyPreFilter`): Logs request paths and headers
- **Global Post-filter** (`MyPostFilter`): Response-level interceptors
- **Custom Filter Factory** (`AuthorizationHeaderFilter`): Validates JWT Bearer tokens on protected routes

## Dependencies
- **MicroLearnDiscovery** (Eureka): Route resolution via service registry
- **UserAccountService**: `/users/**` routes
- **AccountManagment**: `/account/**` routes
- **doc-manager**: `/docs/**` routes

## Status
### Implemented
- [x] Spring Cloud Gateway with Eureka discovery
- [x] JWT validation via `AuthorizationHeaderFilter`
- [x] Route definitions for UserAccountService, AccountManagment, doc-manager
- [x] Global pre/post logging filters
- [x] H2 console route passthrough

### Roadmap
- [ ] Move `jwt.secret` to environment variable/secret manager
- [ ] Add rate limiting filter
- [ ] Add request/response body logging
- [ ] Error response enrichment (structured JSON)
- [ ] CORS configuration for frontend integration
