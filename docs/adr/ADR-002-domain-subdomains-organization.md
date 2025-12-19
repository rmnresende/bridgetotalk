# ADR-002 – Domain Organization into Subdomains

## Status
Accepted

## Date
2025-12-19

## Context

The BridgeToTalk project models a customer service platform with multiple
business concepts such as companies, agents, queues, conversations and
messages.

Although the system currently operates as a single deployable service,
the domain already exhibits clear conceptual boundaries and varying
levels of responsibility and complexity.

As the domain grows, keeping all domain classes in a flat package
structure would reduce readability, increase cognitive load, and make
future evolution or extraction of components harder.

## Decision

The domain is organized into **logical subdomains**, grouped by business
responsibility, while still remaining part of a **single bounded
context**.

The chosen subdomains are:

- **organization**  
  Responsible for company-related concepts and organizational identity.

- **people**  
  Responsible for human actors involved in the system, such as agents and
  customers.

- **attendance**  
  Responsible for customer service operations, including queues,
  conversations, messages and business hours.

This organization is purely logical and does not imply service
segregation or independent deployment.

## Alternatives Considered

### 1. Flat Domain Package

All domain classes placed directly under a single `domain` package.

**Rejected because:**
- Poor scalability as the domain grows
- Low discoverability of related concepts
- Increased risk of accidental coupling

### 2. Multiple Bounded Contexts or Services

Early separation into independent services or bounded contexts.

**Rejected because:**
- Premature optimization
- Increased operational and conceptual complexity
- No current need for independent deployment or ownership

## Consequences

### Positive

- Improved readability and navigation of the domain
- Clear ownership of concepts and rules
- Better alignment with domain language
- Easier future refactoring or service extraction
- Supports gradual evolution without premature microservices

### Negative

- Slight increase in package structure complexity
- Requires discipline to avoid cross-subdomain leakage

These trade-offs are considered acceptable given the expected growth of
the domain and the long-term goals of the project.

## Notes

This organization does not prevent future restructuring. If the system
evolves to require multiple bounded contexts or independent services,
these subdomains can serve as natural candidates for extraction.
