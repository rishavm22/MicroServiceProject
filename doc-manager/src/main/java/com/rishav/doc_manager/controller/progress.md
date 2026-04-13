# Package: com.rishav.doc_manager.controller

## Design Doc
REST controller for document management. Exposes template upload, PDF generation, and template listing endpoints.

## Dependencies
- `../service/DocPDFService` — PDF generation and template management

## Status
### Implemented
- [x] `DocPDFController` with `/docs/` endpoints
- [x] `/docs/uploadTemplate` — Multipart DOCX upload
- [x] `/docs/generatePdf` — PDF generation from template
- [x] `/docs/status/check` — Health check
- [x] `/docs/templates` — List available templates

### Roadmap
- [ ] Add template delete endpoint
- [ ] Add template preview endpoint
- [ ] Add PDF download with filename headers

### Test Coverage Status
- [ ] `DocPDFController` — upload multipart, generate PDF, list templates
- [ ] File validation — wrong file type, empty file
