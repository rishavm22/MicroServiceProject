**Title: ADR-001 — JWT-based Stateless Authentication**
**Date: 2025-02-23 | Updated: 2026-04-05**
**Context:** Services need authentication without session overhead; clients are SPAs/mobile apps that can store tokens securely.
**Decision:** JWT (HS256 access + HS512 refresh), Spring Security stateless, gateway validates tokens, services enforce gateway IP whitelist.
**Consequence:** No session management or token revocation; all validation relies on secret key security — key rotation requires downtime.
