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
- Never modify IDE run configs or CI pipelines without asking.

# Tech Stack

| Component | Version | Note |
|-----------|---------|------|
| JDK | 17 (majority), 11 (legacy) | Check pom.xml for per-module versions |
| Framework | 2.x–3.x (varies) | Check each module independently |
| Build | Maven (mvnw) | All modules |
| Security | Spring Security + JWT | Stateless |
| Database | H2 (dev) | Production TBD |
| Testing | JUnit 5 (all modules) | Mockito version varies by Boot version |
