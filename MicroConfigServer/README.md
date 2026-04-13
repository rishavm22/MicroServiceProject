# MicroConfigServer

## Purpose
Spring Cloud Config Server backed by a remote GitHub repository. Serves centralized configuration to all downstream microservices.

## Configuration
| Property | Value |
|----------|-------|
| Port | `8012` |
| Spring Boot | `3.4.5` |
| Spring Cloud | `2024.0.1` |
| Config Source | GitHub: `github.com/rishavm22/MicroServiceProject-MicroConfigServer` |
| Default Label | `main` |
| Clone on Start | `true` |

## Run
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## Required Local Configuration
Create `src/main/resources/application-local.properties`:
```properties
spring.cloud.config.server.git.username=your-github-username
spring.cloud.config.server.git.password=your-github-pat
```

Or set in IntelliJ Run Configuration:
- `CONFIG_SERVER_GIT_USERNAME`
- `CONFIG_SERVER_GIT_PASSWORD`

## Dependencies
- Spring Cloud Config Server (`spring-cloud-config-server`)
- Spring Cloud Config Client (`spring-cloud-starter-config`)

## Consumed By
All microservices that use `spring-cloud-starter-bootstrap` and `spring-cloud-starter-config`.
