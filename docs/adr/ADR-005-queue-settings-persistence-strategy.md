# ADR-005 – Queue Settings Persistence Strategy

## Status
Accepted

## Date
2025-01-19

## Context

The Queue aggregate includes a set of configuration rules represented by
the `QueueSettings` value object. These settings include automated
messages, business hours and scheduling rules that directly influence
queue behavior and conversation routing.

The internal structure of `QueueSettings`, especially the weekly business
schedule, is relatively complex and expected to evolve over time (e.g.
holidays, exceptions, special rules). At the same time, queue settings are:

- Always accessed together with the Queue aggregate
- Read frequently
- Updated infrequently
- Not queried independently
- Subject to strong business invariants

Given these characteristics, a persistence strategy needed to be chosen
that balances consistency, flexibility and long-term maintainability.

---

## Decision

The Queue aggregate, including its settings, will be persisted using a
**relational database as the primary data store**, with a **hybrid
persistence approach**:

- Core queue attributes and simple settings will be stored in relational
  columns.
- Complex configuration structures, such as weekly schedules, will be
  persisted using a structured JSON (e.g. JSONB in PostgreSQL).
- QueueSettings will remain part of the Queue aggregate and will not be
  persisted in a separate data store.

No NoSQL database will be introduced for QueueSettings at this stage.

---

## Alternatives Considered

### Option 1 – Fully Normalized Relational Model

Persist QueueSettings, WeeklySchedule, DailySchedule and TimeRange as
separate relational tables with explicit relationships.

**Pros:**
- Strong relational integrity
- Easy to query individual schedule components
- Explicit schema representation

**Cons:**
- Excessive number of tables for a tightly coupled aggregate
- Increased join complexity
- Reduced alignment with the aggregate boundaries
- Higher maintenance and mapping overhead

---

### Option 2 – NoSQL Store for QueueSettings

Persist QueueSettings in a document-oriented NoSQL database while keeping
Queue in a relational database.

**Pros:**
- Flexible schema for evolving configuration
- Natural representation of nested structures

**Cons:**
- Breaks aggregate consistency across data stores
- Requires cross-store consistency handling
- Introduces operational and transactional complexity
- Creates multiple sources of truth for a single aggregate

---

### Option 3 – Hybrid Relational + JSON (Chosen)

Persist Queue as a relational entity while storing complex configuration
structures inside JSON columns.

**Pros:**
- Preserves aggregate integrity and transactional consistency
- Aligns persistence model with domain boundaries
- Supports flexible evolution of configuration structures
- Avoids unnecessary database and operational complexity

**Cons:**
- Requires explicit domain-to-persistence mapping
- Limited ability to query internal JSON structures directly

---

## Consequences

### Positive
- Queue and QueueSettings remain a single cohesive aggregate
- Strong transactional consistency is preserved
- Domain model remains independent of persistence concerns
- Future extensions (e.g. holidays, special schedules) are easier to add
- Clear separation between domain modeling and storage strategy

### Negative
- Some internal schedule data is not easily queryable via SQL
- Requires discipline when evolving JSON structure
- Persistence logic becomes slightly more explicit

---

## Notes

This decision aligns with the current scale and access patterns of the
system. If message volume, read models or access patterns change
significantly in the future, alternative storage strategies (including
NoSQL) may be reconsidered for other bounded contexts, such as
conversations or messages.
