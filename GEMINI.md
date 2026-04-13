# Project Context & Rules

Read and follow the rules in `.ai-context/conventions.md` strictly. This is the Global Rulebook.

# Workflow

Every session, you must:
1. Read `TODO.md` for global milestones.
2. Read `README.md` for architecture overview.
3. Read `progress.md` in any folder you modify — check the Design Doc and roadmap before coding.
4. After finishing the task, update the affected `progress.md` files and `TODO.md`.

# Scope Rules

- Do NOT read the entire codebase. Use context files as a map, then drill into specific files.
- Confirm approach before writing complex logic.
- Identify if changes affect downstream components before modifying code.

# Safety Rules

- Never delete files without confirming.
- Never force-push or run destructive git operations.
- Never introduce secrets or credentials into tracked files.
- Never modify `.run/` configs or CI pipelines without asking.

# Tech Stack

| Component | Version |
|-----------|---------|
| JDK | 17 (most services), 11 (doc-manager) |
| Spring Boot | 3.3.6–3.4.5 |
| Spring Cloud | 2023.0.3–2024.0.1 |
| Build | Maven (mvnw) |
| Security | Spring Security + JWT (jjwt 0.11.5/0.12.6) |
| Database | H2 in-memory (dev) |
| Document Processing | Apache POI 4.1.2, FOP 2.9, docx4j 11.5.0 |
| API Gateway | Spring Cloud Gateway (WebFlux) |
| Service Discovery | Netflix Eureka |
| Config Server | Spring Cloud Config Server (Git-backed) |
