# Package: exceptions/

## Design Doc
Global exception handling layer. `AppExceptionHandler` (`@ControllerAdvice`) catches unhandled exceptions and returns structured error responses. Custom exception types (`UsernameException`, `ValidationException`, `BaseRuntimeException`) extend standard exceptions for domain-specific error signaling.

## Dependencies
- `error/` — Structured error message formats (`ErrorMessage`, `ErrorCode`, `GlobalErrorCode`)

## Status
### Implemented
- [x] Global `@ControllerAdvice` handler
- [x] `UsernameException` — thrown on user lookup failures
- [x] `ValidationException` — input validation failures
- [x] `BaseRuntimeException` — base for custom runtime errors

### Roadmap
- [ ] Add specific handler methods per exception type (currently `handleAnyException` catches all)
- [ ] Add MethodArgumentNotValidException handler for @Valid failures
- [ ] Return structured error codes instead of raw messages

### Test Coverage Status
- [ ] `AppExceptionHandler` — returns structured error for each exception type
- [ ] `ValidationException` — proper HTTP status and message
- [ ] `UsernameException` — email-based error messages
