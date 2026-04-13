# doc-manager

## Purpose
Document management microservice. Upload DOCX templates, populate placeholders with data, and generate PDF documents.

## Configuration
| Property | Value |
|----------|-------|
| Port | `8087` (static) |
| Spring Boot | `2.7.13` |
| Spring Cloud | `2021.0.3` |
| Java | `11` |
| Eureka | `http://localhost:8091/eureka` |

## Run
```bash
mvn spring-boot:run
```

## Endpoints

| Method | Path | Description |
|--------|------|-------------|
| GET | `/docs/status/check` | Health check |
| POST | `/docs/uploadTemplate` | Upload a DOCX template |
| POST | `/docs/generatePdf` | Generate PDF from template + data |
| GET | `/docs/templates` | List available templates |

## Architecture
- **Controller**: `DocPDFController`
- **Service**: `DocPDFService`
- **Utilities**: `DocxPlaceholderReplacer`, `CustomNamespacePrefixMapper`

## Document Processing Libraries
- **Apache POI** 4.1.2 — DOCX reading/manipulation
- **docx4j** 11.5.0 — DOCX placeholder replacement
- **Apache FOP** 2.9 — PDF rendering
- **JAXB** 3.0.1/3.0.2 — XML binding

## Dependencies
- **Infrastructure**: MicroLearnDiscovery (Eureka), APIGateway (routing)

## Testing
| Aspect | Value |
|--------|-------|
| **JUnit** | JUnit 5 (Jupiter) — Boot 2.7+ supports it |
| **Assertions** | AssertJ |
| **Mocking** | Mockito 4 |
| **Integration** | `@SpringBootTest` + `@AutoConfigureMockMvc` |
| **Swagger UI** | `http://localhost:8087/swagger-ui.html` |
