# Testing Strategy

## 1. Framework Selection (Version-Aware)

**Before generating any test, check the target service's pom.xml for Spring Boot version:**

| Service | Boot Version | Java | JUnit | Assertions | Mocking |
|---------|-------------|------|-------|-----------|---------|
| MicroLearnDiscovery | 3.3.6 | 17 | **JUnit 5** | AssertJ | Mockito 5 |
| MicroConfigServer | 3.4.5 | 17 | **JUnit 5** | AssertJ | Mockito 5 |
| APIGateway | 3.4.3 | 17 | **JUnit 5** | `StepVerifier` (reactor-test) | Spring WebTestClient |
| UserAccountService | 3.3.6 | 17 | **JUnit 5** | AssertJ | Mockito 5 |
| AccountManagment | 3.4.3 | 17 | **JUnit 5** | AssertJ | Mockito 5 |
| doc-manager | **2.7.13** | 11 | **JUnit 5** | AssertJ | Mockito 4 |

**All services currently use JUnit 5** (Boot 2.7+ supports Jupiter out of the box). If a future legacy module requires JUnit 4, use:
- `@RunWith(SpringRunner.class)` instead of `@SpringBootTest`
- `assertThat()` from `org.junit.Assert` instead of `org.assertj.core.api.Assertions`
- Mockito 3

## 2. Test Layer Strategy

### 2.1 Unit Tests
- **Target**: Services, controllers, utilities, exception handlers
- **Pattern**: Test class naming — `*Test.java` (not `*Tests.java` for unit)
- **Approach**: Mock dependencies with `@ExtendWith(MockitoExtension.class)`
- **Assertions**: AssertJ fluent assertions (`assertThat(actual).isEqualTo(expected)`)

### 2.2 Integration Tests
- **Target**: Controllers with real service layer, repository DB queries
- **Pattern**: `@SpringBootTest` + `@AutoConfigureMockMvc` for MVC endpoints
- **APIGateway**: Use `WebTestClient` with `StepVerifier` for reactive routes
- **Database**: Use H2 for UserService integration tests; test slices with `@DataJpaTest`

### 2.3 Contract Tests (Future)
- Once the `api-spec/openapi.yaml` is stable, use Spring Cloud Contract or Pact to validate service contracts against the gateway routes.

## 3. Test Data Factory Pattern

**Standard: Hybrid Factory (Object Mother + Fluent Builder)**

Create shared test data factories in `src/test/java/.../testutil/` in each service:

```java
// Object Mother: quick factory for common scenarios
public class UserTestData {
    public static UserDTO validUser() { ... }
    public static UserDTO userWithRoleAdmin() { ... }
}

// Fluent Builder: for customized test data
public class UserDTOBuilder {
    private UserDTO instance = new UserDTO();
    public static UserDTOBuilder aUser() { return new UserDTOBuilder(); }
    public UserDTOBuilder withFirstName(String name) { instance.setFirstName(name); return this; }
    public UserDTOBuilder withEmail(String email) { instance.setEmail(email); return this; }
    public UserDTO build() { return instance; }
}
// Usage: aUser().withEmail("test@test.com").withFirstName("Test").build()
```

**Rules:**
- Keep test data in `testutil/` package per service
- Use builders for edge cases, object mothers for common happy-path data
- Never use real user credentials in tests

## 4. What to Test (Priority Order)

1. **Auth flow** — login success/failure, token generation, invalid credentials
2. **User CRUD** — create with validation, update, list, duplicate email rejection
3. **Gateway routes** — JWT validation (valid/invalid/missing token), routing to downstream services
4. **Document generation** — template upload, PDF output, invalid template handling
5. **Exception handling** — structured error responses for all known exception types

## 5. Coverage Targets

| Layer | Target |
|-------|--------|
| Services | 80%+ (core logic) |
| Controllers | 70%+ (endpoint mapping, validation) |
| Security Config | 90%+ (auth filter, token validation) |
| Repositories | Via `@DataJpaTest` (CRUD + custom queries) |
| Config classes | Not required |

## 6. Running Tests

```bash
# Run all tests in a service
cd <service> && mvn test

# Run a single test class
cd <service> && mvn test -Dtest=UserServiceTest

# Run with coverage (add jacoco plugin first)
cd <service> && mvn test jacoco:report
```
