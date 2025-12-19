# BridgeToTalk – Documentation Index

This directory contains the official documentation for the BridgeToTalk
project. The documents are organized to clearly separate **decisions**,
**business behavior**, **architecture**, and **future explorations**.

The goal is to make the system understandable, evolvable and auditable
over time — both for current contributors and for future maintainers.

---

## Documentation Structure

```text
docs
 ├── adr
 ├── architecture
 ├── business-rules
 ├── design-notes
 └── README.md
```
Each folder serves a distinct purpose and should be used intentionally.

---

## Architecture

📁 docs/architecture

Describes the **current technical structure** of the system.

These documents explain how the system is organized today, without
focusing on historical decisions or future speculation.

### Contents:

- overview.md – High-level system overview

- hexagonal-architecture.md – Practical application of Hexagonal Architecture

- domain-structure.md – Domain subdomains and dependency rules

- request-flow.md – How requests flow through the system

Use this section to understand:

- Where each concern belongs

- How layers interact

- How the architecture is applied in practice

---

## Architecture Decision Records (ADRs)

📁 docs/adr

ADRs document **architectural and design decisions** that were made, along
with their context, alternatives and consequences.

ADRs are **historical records** and should not be edited after being
accepted. If a decision changes, a new ADR must be created referencing
the previous one.

Current ADRs:

ADR-001 – Adoption of Hexagonal Architecture

ADR-002 – Domain Subdomains Organization

ADR-003 – Queue Business Hours Modeling

ADR-004 – Domain Exceptions Strategy

Use ADRs to understand:

- Why the system is designed the way it is

- What trade-offs were considered

- How architectural decisions evolved over time

---
Business Rules

📁 docs/business-rules

Business Rules describe **mandatory domain behavior** and real-world
constraints modeled by the system.

These documents answer how the business works, independent of technical
implementation details.

Examples include:

- Queue business hours

- Agent availability

- Agent–queue association rules

Conversation entry rules

Business Rules must be:

- Explicit

- Deterministic

- Testable

They should not describe technical solutions or infrastructure concerns.

---

Design Notes

📁 docs/design-notes

Design Notes capture **ideas, hypotheses and explorations** that have not
yet resulted in a formal decision.

They are intentionally lightweight and may represent:

- Potential new features

- Possible business rules

- Candidate technical optimizations

- Architectural explorations

Design Notes are not commitments. They exist to prevent ideas from
being lost and to support informed decisions later.

When a Design Note leads to a concrete decision, it should be promoted to
an ADR or Business Rule.

---

**Documentation Flow**

The documentation follows this lifecycle:

```
Idea → Design Note
Decision → ADR
Defined Behavior → Business Rules
Stable Structure → Architecture Docs
```

This separation ensures clarity, traceability and long-term maintainability.

---

## Contribution Guidelines (Documentation)

When contributing to the project:

- Use Design Notes to capture ideas and hypotheses

- Use ADRs only for finalized decisions

- Update Business Rules when behavior changes

- Keep Architecture docs aligned with the current structure

Avoid mixing concerns across document types.

---

## Notes

This documentation is intended to evolve alongside the system.
Significant changes in architecture, domain behavior or technical strategy
must be reflected through the appropriate documentation artifacts.