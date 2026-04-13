# Package: services/user/dto/

## Design Doc
Generic user DTOs used for data transfer between layers. `UserDTO` is the base representation used by both controllers and services.

## Dependencies
- `../../entities/user/User` — Source entity for ModelMapper conversions

## Status
### Implemented
- [x] `UserDTO` — Base user data transfer object
