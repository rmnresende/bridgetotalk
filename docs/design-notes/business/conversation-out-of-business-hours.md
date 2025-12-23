# Design Note – Conversations Initiated Outside Business Hours

## Status
Exploration

## Date
2025-12-19

## Context

Customers may initiate contact with the company at any time, including
outside defined business hours. These messages often represent important
or sensitive issues (e.g. complaints, delivery problems, refunds) that
must not be lost or ignored.

In small and medium companies, agent availability may be irregular, and
conversations may only be handled hours later or on the following day.

---

## Motivation

The system must ensure that:

- No customer message is lost
- Conversations preserve full context
- Off-hours messages can be handled naturally when business hours resume
- The domain model remains simple and consistent

Blocking conversation creation outside business hours introduces hidden
state, technical workarounds and loss of business intent.

---

## Idea / Hypothesis

Separate the concepts of **message reception** and **agent attendance**.

Key principles under consideration:

- Conversations are created immediately upon message reception.
- Business hours control routing eligibility, not conversation existence.
- Messages received outside business hours are stored and visible to
  agents when attendance resumes.
- Off-hours context may influence future prioritization or routing rules.

---

## Possible Approaches

### 1. Delay Conversation Creation
Store messages temporarily and create conversations later.

- High risk of data loss
- Hidden state
- Increased complexity

---

### 2. Immediate Conversation Creation (Preferred)
Create the conversation immediately and control routing via business
hours.

- Preserves full context
- Simplifies domain rules
- Aligns with real-world support workflows

---

## Trade-offs & Considerations

- Conversations may exist without immediate agent assignment
- Requires clear distinction between existence and eligibility
- Status modeling must avoid excessive transitional states

---

## Open Questions

- Should off-hours entry context influence routing priority?
- Should customers be notified when attendance resumes?
- How does this interact with future skill-based routing?

---

## Not a Decision

This document represents an exploration of conversation lifecycle behavior
outside business hours. No prioritization or routing decisions have been
finalized.
