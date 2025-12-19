# ADR-004 – Domain Exceptions Strategy

## Status
Accepted

## Date
2025-12-19

## Context

The BridgeToTalk domain contains multiple business rules related to
companies, agents, queues, availability, and attendance behavior.

As the domain evolved, different types of exceptional situations emerged,
including:
- Missing resources (e.g. agent, queue, company)
- Violations of business rules
- Conflicts caused by duplicated data

Without a clear strategy, exceptions could become inconsistent, overly
generic, or tightly coupled to infrastructure concerns, making the domain
harder to reason about and evolve.

## Decision

The project adopts a **domain-oriented exception strategy** with the
following principles:

1. **Exceptions are part of the domain model** and represent invalid states
   or rule violations.
2. **Generic domain exceptions** that apply across subdomains are placed in
   a shared package.
3. **Specific exceptions** are placed within their respective subdomains.
4. The domain does not expose infrastructure or transport concerns
   (e.g. HTTP, persistence) through exceptions.

### Exception Organization

- **Shared domain exceptions**
    - `BusinessException`
    - `ResourceNotFoundException`
    - `ResourceAlreadyExistsException`

  Located under:
  ```text
  domain/shared/exception
  ```

### Subdomain-specific exceptions

- AgentNotFoundException → people

- QueueNotFoundException → attendance

- CompanyNotFoundException → organization

Located close to the domain concepts they represent.

## Alternatives Considered
### 1. Single Centralized Exception Package

All exceptions placed under a single exception package.

**Rejected because:**

- Loses domain context

- Reduces clarity of ownership

- Scales poorly as the domain grows

### 2. Infrastructure-Driven Exceptions Only

Relying primarily on persistence or framework exceptions.

**Rejected because:**

- Leaks technical concerns into the domain

- Makes business rules implicit

- Reduces expressiveness and testability

### 3. Purely Generic Exceptions

Using only a small set of generic exceptions for all error cases.

**Rejected because:**

- Hides domain intent

- Makes error handling less precise

- Reduces readability and debuggability

## Consequences
### Positive

- Clear separation between domain and infrastructure concerns

- High expressiveness of business rules

- Improved readability and maintainability

- Easier mapping of domain errors to API responses

- Natural alignment with subdomain boundaries

### Negative

- Slight increase in the number of exception classes

- Requires discipline to avoid misuse or duplication

These trade-offs are considered acceptable given the benefits in clarity
and long-term maintainability.

## Notes

- Domain exceptions may be translated to transport-level responses
(e.g. HTTP status codes) outside the domain layer.

- This strategy does not prevent future refactoring if the exception model
needs to evolve.

