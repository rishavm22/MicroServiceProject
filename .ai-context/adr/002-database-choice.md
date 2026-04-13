**Title: ADR-002 — In-Memory H2 for Development, No Production DB yet**
**Date:** 2025-03-25 | **Updated:** 2026-04-05
**Context:** UserService needs persistent storage for user data; project is in early development.
**Decision:** H2 in-memory (`jdbc:h2:mem:testdb`) for UserService; AccountManagment has MySQL dependency (unused); doc-manager has no datastore.
**Consequence:** Data is lost on restart; no transaction management across services; MySQL dependency in AccountManagment adds unused weight.
