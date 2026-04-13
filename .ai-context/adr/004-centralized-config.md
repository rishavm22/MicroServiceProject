**Title: ADR-004 — Git-Backed Centralized Configuration**
**Date:** 2025-04-04 | **Updated:** 2026-04-05
**Context:** Config properties were scattered across services, making updates and secret management error-prone.
**Decision:** Spring Cloud Config Server on port 8012, config sourced from a private GitHub repository with clone-on-start.
**Consequence:** Config server depends on git availability; credentials leaked in commit history require token rotation; services must start after config server is ready.
