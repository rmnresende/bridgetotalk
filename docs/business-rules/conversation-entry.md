# Conversation Entry & Attendance Rules

## Context

A Conversation represents the lifecycle of communication between a
customer and the company through the system. It aggregates all messages,
status transitions and routing decisions related to a single customer
interaction.

Conversations are routed through queues and handled by agents, but their
existence is independent of agent availability or business hours.

---

## Core Rules

1. A conversation is **always created** when a customer message is
   received, regardless of queue business hours.

2. Queue business hours define **when a conversation becomes eligible for
   attendance**, not whether it exists.

3. Messages received outside business hours:
    - Are persisted as part of the conversation.
    - Are not routed to agents immediately.
    - May trigger automated off-hours messages, if configured.

4. A conversation becomes eligible for agent routing only when:
    - The associated queue is open for attendance.
    - At least one agent is available.

5. When an agent is assigned to a conversation, the conversation enters
   the `IN_ATTENDANCE` state.

6. Conversation status transitions must preserve the full message history
   and customer context at all times.

---

## Conversation Status Semantics

The system uses conversation status to represent **intent and lifecycle**,
not temporary technical conditions.

Recommended high-level statuses:

- `WAITING_FOR_ATTENDANCE`  
  The conversation exists and is awaiting agent handling.

- `IN_ATTENDANCE`  
  An agent is actively handling the conversation.

- `CLOSED`  
  The conversation has been finalized.

Contextual information (e.g. initiated outside business hours) must be
represented separately from the core status.

---

## Notes

- Business hours must never cause loss of customer messages.
- Routing rules may evolve independently from conversation lifecycle.
- Future prioritization strategies may consider message content, entry
  context or customer history.
