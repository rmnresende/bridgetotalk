# Hexagonal Architecture in BridgeToTalk

## Purpose

This document explains how **Hexagonal Architecture (Ports and Adapters)**
is applied in the BridgeToTalk project, focusing on practical structure,
responsibilities and boundaries rather than theoretical definitions.

The goal is to make architectural rules explicit, prevent erosion over
time, and help contributors understand where each concern belongs.

---

## Architectural Principle

BridgeToTalk follows a **domain-centric architecture**, where:

- The **domain** represents the core business rules
- The **application layer** orchestrates use cases
- **Adapters** handle external interactions
- Dependencies always point **inward**

This structure ensures that business logic remains independent from
frameworks, transport protocols and infrastructure details.

---

## High-Level Dependency Rule

The fundamental rule enforced in the project is:

Adapters → Application → Domain


- The domain depends on nothing
- The application depends only on the domain
- Adapters depend on application and domain

Reverse dependencies are not allowed.

---

## Layer Responsibilities

### Domain Layer

The domain layer contains:

- Entities
- Value Objects
- Domain-specific exceptions
- Core business rules and invariants

Characteristics:

- No framework annotations
- No persistence concerns
- No transport concerns
- Fully testable in isolation

Examples:
- Agent availability rules
- Queue business hours
- Association rules between agents and queues

---

### Application Layer

The application layer coordinates **use cases** and defines **ports**
that represent the required interactions with external systems.

Responsibilities:

- Orchestrate domain objects
- Enforce application-level workflows
- Define input and output ports
- Translate commands into domain operations

Characteristics:

- No HTTP or persistence code
- No framework-specific annotations that leak into the domain
- Clear use case boundaries

Examples:
- Linking an agent to a queue
- Creating a queue
- Evaluating whether attendance is allowed

---

### Inbound Adapters

Inbound adapters expose the application to the outside world.

Typical responsibilities:

- Handle HTTP requests
- Validate input format
- Map DTOs to application commands
- Invoke use cases through input ports

Characteristics:

- Framework-dependent (e.g. Spring MVC)
- No business logic
- No direct access to persistence

Examples:
- REST controllers
- Request/response DTOs
- Input validation

---

### Outbound Adapters

Outbound adapters implement ports defined by the application layer.

Typical responsibilities:

- Persistence
- External service communication
- Technical integrations

Characteristics:

- Framework-dependent (e.g. JPA)
- Implement repository ports
- Map domain objects to persistence models

Examples:
- JPA repositories
- Database mappings
- Infrastructure services

---

## Ports and Adapters in Practice

### Ports

Ports are defined in the **application layer** and express what the
application needs from the outside world.

Examples:
- AgentRepositoryPort
- QueueRepositoryPort
- AgentQueueRepositoryPort

Ports are expressed as interfaces and depend only on the domain model.

---

### Adapters

Adapters implement ports and live outside the core application.

Examples:
- JPA repository adapters
- External integration adapters

Adapters can be replaced or modified without impacting domain logic.

---

## Example: Request Flow

A typical request follows this path:

**HTTP Request  → Inbound Adapter (Controller)  → Application Use Case  → Domain Logic  → Outbound Adapter (Repository)**

The domain remains unaware of:
- HTTP
- JSON
- Databases
- Frameworks

---

## Error Handling Strategy

Exceptions follow the same architectural boundaries:

- Domain exceptions represent invalid states or rule violations
- Application and adapters translate domain exceptions into
  transport-specific responses
- No infrastructure exceptions leak into the domain

This strategy is documented in **ADR-004 – Domain Exceptions Strategy**.

---

## What Is Explicitly Not Allowed

To preserve architectural integrity, the following practices are
explicitly discouraged:

- Domain classes depending on Spring, JPA or other frameworks
- Controllers accessing repositories directly
- Business rules implemented in adapters
- Persistence models leaking into the domain

Violations of these rules weaken the architecture and increase coupling.

---

## Benefits of This Approach

Applying hexagonal architecture in BridgeToTalk provides:

- Clear separation of concerns
- High testability of the domain
- Strong alignment between code and business language
- Easier refactoring and evolution
- Preparation for future module or service extraction

---

## Relationship with ADRs

The adoption of hexagonal architecture and its rationale are documented in
**ADR-001 – Adoption of Hexagonal Architecture**.

This document focuses on **how the architecture is applied**, while the
ADR explains **why the decision was made**.

---

## Notes

This document describes the current architectural approach. As the
system evolves, additional constraints or refinements may be documented
through new ADRs.


