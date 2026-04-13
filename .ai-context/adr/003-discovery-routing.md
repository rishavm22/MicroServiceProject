**Title: ADR-003 — Eureka + Spring Cloud Gateway for Service Discovery & Routing**
**Date:** 2025-02-23 | **Updated:** 2026-04-05
**Context:** Multiple microservices need dynamic discovery and routing without hardcoded URLs or manual LB config.
**Decision:** Netflix Eureka Server as service registry; Spring Cloud Gateway with `lb://` URIs for load-balanced routing; Gateway on fixed port 8082, discovery on 8091.
**Consequence:** Eureka is a single point of failure (no HA peer replication); dynamic service ports (0) prevent direct client access to services.
