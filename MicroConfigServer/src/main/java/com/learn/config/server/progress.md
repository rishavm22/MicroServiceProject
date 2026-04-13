# MicroConfigServer Module

## Design Doc
Spring Cloud Config Server backed by a remote GitHub repository (`github.com/rishavm22/MicroServiceProject-MicroConfigServer`). Serves centralized configuration to downstream microservices. Runs on port 8012.

## Dependencies
- **GitHub repo** (`MicroServiceProject-MicroConfigServer`): Config file source
- **Spring Cloud Config Server** (`spring-cloud-config-server`): Core config server
- No downstream service dependencies — other services consume from this.

## Status
### Implemented
- [x] Spring Cloud Config Server with Git backend
- [x] Clone-on-start and default label (`main`)
- [x] Environment variable-based Git credentials

### Roadmap
- [ ] Migrate to SSH key or OAuth app-based auth (remove username/password)
- [ ] Add config encryption (Jasypt or Spring Cloud Vault)
- [ ] Monitor/config health endpoint dashboard
- [ ] Config change webhook for hot-reload
