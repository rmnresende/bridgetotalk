# ADR-001 – Adoption of Hexagonal Architecture

## Status
Accepted

## Date
2025-12-19

## Context

The BridgeToTalk project is being developed as a learning-oriented but
production-inspired system, aiming to model a real-world customer
service platform that could be used by small and medium-sized companies,
and potentially scale to larger organizations.

The domain involves non-trivial business rules (queues, agents,
availability, business hours, conversations, messages) and is expected
to grow in complexity over time.

Given this context, architectural decisions must balance:
- Learning value
- Long-term maintainability
- Ease of evolution
- Alignment with real market practices

## Decision

The project adopts **Hexagonal Architecture (Ports and Adapters)** as its
primary architectural style.

This architecture was chosen to:
- Practice and internalize a widely used market pattern
- Explicitly separate domain logic from technical concerns
- Promote a domain-centric design
- Enable easier future evolution, refactoring, and potential service splits

The domain layer is kept free from framework dependencies, while
application logic communicates with the outside world exclusively
through ports.

## Alternatives Considered

### 1. Traditional Layered Architecture (Controller → Service → Repository)

- Simpler initial setup
- Less boilerplate code

**Rejected because:**
- Tends to blur domain and infrastructure concerns
- Makes long-term evolution harder
- Encourages anemic domain models

### 2. Framework-Centric Architecture

- Faster initial development
- Less explicit boundaries

**Rejected because:**
- Strong coupling to frameworks
- Reduced testability of the domain
- Harder to reason about business rules independently

## Consequences

### Positive

- Clear separation of concerns
- Highly expressive domain model
- Improved testability of business logic
- Easier future extraction of modules or services
- Better long-term maintainability and readability
- Alignment with real-world, production-grade architectures

### Negative

- Increased initial complexity
- Higher amount of boilerplate code
- Slower initial development speed

These trade-offs are considered acceptable given the project’s learning
goals and long-term vision.

## Notes

This decision is not considered immutable. If future requirements or
constraints significantly change, this architectural choice may be
revisited and superseded by a new ADR.
