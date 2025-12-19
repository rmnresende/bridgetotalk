
### Domain
The domain layer contains the core business model and rules, including
entities, value objects and domain-specific exceptions. It is completely
free of framework and infrastructure dependencies.

### Application
The application layer orchestrates use cases, coordinates domain
objects, and defines ports for external interactions. It represents the
application-specific behavior of the system.

### Adapters
Adapters connect the application to the outside world.

- **Inbound adapters** handle external input (e.g. HTTP requests).
- **Outbound adapters** implement ports to interact with persistence or
  other infrastructure concerns.

---

## Domain Organization

The domain is organized into logical subdomains to improve readability,
clarity and future evolution, while remaining a single bounded context.

The main subdomains are:

- **organization**  
  Company-related concepts and organizational identity.

- **people**  
  Human actors involved in the system, such as agents and customers.

- **attendance**  
  Core customer service operations, including queues, conversations,
  messages and business hours.

- **shared**  
  Domain concepts shared across subdomains, such as generic domain
  exceptions.

This organization is logical only and does not imply independent
deployment or service boundaries.

---

## Core Concepts

Some of the key domain concepts include:

- **Company** – Represents an organization using the platform.
- **Agent** – A human operator responsible for handling conversations.
- **Queue** – Defines how conversations are routed and when attendance is
  allowed.
- **Queue Settings** – Operational configuration for queues, including
  business hours and automated messages.
- **Conversation** – Represents the lifecycle of interaction between a
  customer and the system.
- **Message** – Individual communication units within a conversation.

---

## Design Goals

The architecture is guided by the following goals:

- Explicit and expressive business rules
- Clear separation of concerns
- Ease of testing and reasoning about the domain
- Support for gradual growth in complexity
- Preparation for future refactoring or service extraction

These goals influence both the structure of the codebase and the
decisions documented through Architecture Decision Records (ADRs).

---

## Documentation Structure

The project documentation is organized as follows:

- **Architecture**  
  High-level technical structure and system organization.

- **Business Rules**  
  Explicit documentation of domain rules and behavior.

- **ADR (Architecture Decision Records)**  
  Records of significant architectural and design decisions, including
  context, alternatives and consequences.

---

## Notes

This overview intentionally avoids low-level technical details such as
database schemas, deployment infrastructure or framework configuration.
Those aspects are documented separately as the system evolves.
