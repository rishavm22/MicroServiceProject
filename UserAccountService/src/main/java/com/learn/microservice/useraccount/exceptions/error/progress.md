# Package: exceptions/error/

## Design Doc
Error response modeling. Structured error codes (`ErrorCode`, `GlobalErrorCode`) and message formats (`ErrorMessage`) for consistent API error responses.

## Dependencies
- `../AppExceptionHandler` — Uses these types in exception handling

## Status
### Implemented
- [x] `ErrorMessage` — Simple error response body
- [x] `ErrorCode` — Error code definitions
- [x] `GlobalErrorCode` — Global error classifications
- [x] `ValidationException` — Validation-specific error type

### Roadmap
- [ ] Add error codes for auth-specific failures
- [ ] Add RFC 7807 ProblemDetails support
