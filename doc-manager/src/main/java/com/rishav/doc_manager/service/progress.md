# Package: com.rishav.doc_manager.service

## Design Doc
Document processing service. `DocPDFService` handles template storage (local filesystem), DOCX placeholder replacement via docx4j, and PDF conversion via Apache FOP.

## Dependencies
- `../DocxPlaceholderReplacer` — Placeholder substitution logic
- Apache POI 4.1.2 — DOCX reading
- docx4j 11.5.0 — DOCX manipulation
- Apache FOP 2.9 — PDF rendering
- `../CustomNamespacePrefixMapper` — XML namespace handling

## Status
### Implemented
- [x] Template upload and storage
- [x] PDF generation from DOCX template + data
- [x] Template listing

### Roadmap
- [ ] Add template deletion
- [ ] Support for more template format types (HTML, RTF)
- [ ] Add caching for frequently used templates

### Test Coverage Status
- [ ] `DocPDFService` — upload, generate PDF, list templates
- [ ] `DocxPlaceholderReplacer` — placeholder substitution with various data types
- [ ] Error handling — missing template, invalid DOCX
