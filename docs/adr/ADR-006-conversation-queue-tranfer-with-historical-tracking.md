# ADR-006 – Conversation Queue Transfer with Historical Tracking

## Status
Accepted

## Date
2025-12-26

## Context

The BridgeToTalk platform models customer support conversations routed through queues.
Initially, a conversation is associated with a single queue at creation time, which is sufficient for basic routing.

However, during domain analysis and documentation of the message intake and conversation lifecycle, it became clear that:

- Manual and automatic queue transfers are a common and essential feature in real-world customer support systems.

- Small and medium companies often:

- Use a default queue for triage.

- Transfer conversations manually to specialized queues.

- Larger companies rely on transfers for:

- Skill-based routing

- Load balancing

- Escalations

Additionally, operational, auditing, and analytical needs require that queue transfer history must be preserved, not overwritten.

The current model implied a strict 1:1 relationship between Conversation and Queue, which does not support historical tracking.

---

## Decision

We decided to model queue transfers as a historical event, while keeping a single active queue on the conversation.

The solution adopts a State + History pattern:

1. Conversation keeps a reference to its current queue
   This represents the active routing context.

2. A new entity is introduced to track queue transfer history
   Every transfer must be explicitly recorded as a domain event.


---

## Domain Model Changes

### Conversation (current state)

The Conversation aggregate maintains:

- queueId → represents the current active queue

- This field is used for:

    - Routing

    - Agent assignment

    - UI representation

    - Performance-critical queries

The relationship remains effectively **1:1 for the current state**.

---

## New Entity: ConversationQueueHistory

A new domain entity is introduced to represent queue transfers:

ConversationQueueHistory
- id
- conversationId
- fromQueueId
- toQueueId
- transferredByAgentId (nullable)
- reason
- transferredAt


Optionally, a TransferReason enum may be used to classify transfers, such as:

- MANUAL

- AUTOMATIC_ROUTING

- OUT_OF_BUSINESS_HOURS

- SKILL_MATCH

- OVERFLOW

---

## Business Rules

- A conversation may be transferred between queues at any time, subject to business validation.

- The conversation always has one and only one active queue.

- Every queue transfer must generate a history record.

- Updating the current queue without persisting a history entry is forbidden.

- Queue transfers do not reset the conversation lifecycle or message history.

---

## Persistence Strategy

### Tables

- `conversations`

   - Stores the current queue (`queue_id`)

- `conversation_queue_history`

   - Stores all queue transfer events

### Performance Considerations

- Queries for active conversations rely only on conversations.queue_id.

- Historical analysis and auditing use conversation_queue_history.

- This avoids expensive joins in the critical message routing path.

---

## Consequences

### Positive

- Enables full auditability of queue transfers.

- Aligns the domain with real-world customer support systems.

- Preserves performance and simplicity for active routing.

- Supports future features:

   - Metrics and analytics

   - Routing optimization

   - SLA and escalation analysis

### Trade-offs

- Introduces an additional table and entity.

- Requires discipline to enforce the invariant that every transfer must be recorded.

These trade-offs are acceptable and expected given the system’s growth goals.

---

## Alternatives Considered
1. Overwriting queueId without history

Rejected — loses critical business information and auditability.

2. Modeling conversation-to-queue as many-to-many

Rejected — complicates querying and does not clearly express the concept of “current queue”.

3. Event sourcing for conversations

Deferred — considered overkill at this stage of the project.

---

## Notes

This decision reinforces the principle that:

> State represents what is true now, history represents how we got here.

This ADR does not preclude future evolution toward event-driven architectures or event sourcing if system scale demands it.

---