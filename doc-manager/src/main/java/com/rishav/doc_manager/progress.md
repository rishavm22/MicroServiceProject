# Package: com.rishav.doc_manager (root)

## Design Doc
Root package for doc-manager. Contains docx4j utilities: `DocxPlaceholderReplacer` for DOCX placeholder substitution, `CustomNamespacePrefixMapper` for XML namespace handling. Also `DocManagerApplication` entry point.

## Dependencies
- `controller/` — REST layer
- `service/` — PDF generation service
- Apache POI 4.1.2, docx4j 11.5.0, FOP 2.9 — Document processing

## Status
### Implemented
- [x] `DocManagerApplication` entry point
- [x] `DocxPlaceholderReplacer` — placeholder substitution in DOCX
- [x] `CustomNamespacePrefixMapper` — XML namespace control
