**Title: ADR-005 — Multi-Version Spring Boot Ecosystem**
**Date:** 2025-05-19 | **Updated:** 2026-04-05
**Context:** doc-manager was built on Spring Boot 2.7.13 (Java 11) with Apache POI/FOP dependencies; other services use Boot 3.x (Java 17).
**Decision:** Keep doc-manager on Boot 2.x to avoid breaking docx4j/JAXB compatibility; use springdoc v1 for Boot 2 services and v2 for Boot 3 services.
**Consequence:** Two different Spring Cloud versions in production (2021.0.3 vs 2024.0.1); incompatible namespace requirements (javax vs jakarta); cannot use shared test infrastructure or parent POM across all services.
