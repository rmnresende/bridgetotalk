# Domain Structure

## Purpose

This document describes the internal structure of the BridgeToTalk domain,
its logical subdomains, and the rules that govern dependencies and
responsibilities between them.

The goal is to preserve domain clarity as the system grows, prevent
accidental coupling, and provide guidance on where new concepts should
be introduced.

---

## Single Bounded Context

BridgeToTalk currently operates as a **single bounded context** focused on
customer service and attendance.

All subdomains share:
- The same ubiquitous language
- The same transactional boundaries
- The same deployment unit

Subdomains are used for **logical organization**, not for service
segregation.

---

## Domain Subdomains Overview

The domain is organized into the following subdomains:

```text
domain
 ├── attendance
 ├── people
 ├── organization
 └── shared
 ```
Each subdomain has a clear responsibility and ownership over its concepts
and rules.

---

## Subdomain Responsibilities

### 1 - Organization

#### Purpose:

Represents organizational identity and configuration.

#### Main responsibilities:

- Company identity

- Company status and lifecycle

- Company-level configuration (e.g. plans, limits)

#### Examples of concepts:

- Company

- CompanySettings

- Plan

This subdomain does not depend on operational attendance logic.

---

### 2 - People

#### Purpose:

Represents human actors involved in customer service.

#### Main responsibilities:

- Agent identity and lifecycle

- Agent availability and status

- Customer representation

#### Examples of concepts:

- Agent

- AgentRole

- AgentStatus

- Customer

This subdomain may reference organizational identity (Company) but does
not contain routing or queue-specific logic.

---

### 3 - Attendance

#### Purpose:
Represents the core customer service operations.

####  Main responsibilities:

- Queues and routing strategies

- Business hours and availability rules

- Conversations and messages

- Agent–queue associations

#### Examples of concepts:

- Queue

- QueueSettings

- WeeklySchedule

- Conversation

- Message

This subdomain is where most operational business rules reside.

---

### 4 - Shared

#### Purpose:
Holds domain concepts shared across multiple subdomains.

#### Main responsibilities:

- Generic domain exceptions

- Shared domain abstractions

#### Examples of concepts:

- BusinessException

- ResourceNotFoundException

- ResourceAlreadyExistsException

This subdomain must remain small and stable.

---

## Dependency Rules

To preserve domain integrity, the following dependency rules apply:

### Allowed Dependencies

- attendance → people

- attendance → organization

- people → organization

- All subdomains → shared

### Disallowed Dependencies

- organization → attendance

- organization → people

- people → attendance

#### These rules ensure that:

- Core organizational identity remains stable

- Operational logic does not leak upward

- Cyclic dependencies are avoided

- Subdomains remain independent of each other

- Subdomains remain stable

---

## Ownership of Business Rules

Business rules must live in the subdomain that owns the concept they
govern.

Examples:

- Agent availability rules → people

- Business hours evaluation → attendance

- Queue routing rules → attendance

- Company limits and plans → organization

- Rules must not be duplicated across subdomains.


---

## Cross-Subdomain Coordination

When behavior requires coordination between subdomains, orchestration
must occur in the application layer, not inside the domain.

Example:

- Linking an agent to a queue requires validation of both agent and queue
state.

- This coordination is handled by a use case, not by either entity alone.

This avoids tight coupling and keeps domain concepts focused.

---

## Evolution Guidelines

As the system evolves:

- New concepts should be placed in the subdomain that owns their rules.

- If a subdomain grows significantly, it may become a candidate for
extraction into a separate bounded context or service.

- Such changes must be documented through new ADRs.

---

## Notes

This structure reflects the current understanding of the domain. It is
expected to evolve as requirements grow, but any significant structural
change should be intentional and documented.