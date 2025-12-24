# Agent Availability & Capacity Rules

## Context

In chat-based customer support systems, agents may handle multiple
conversations simultaneously. Therefore, availability cannot be modeled
as a binary state based solely on agent status.

The BridgeToTalk platform must determine, at runtime, whether an agent
is eligible to receive new conversations, considering both human intent
and operational capacity.

---

## Core Concepts

### Agent Status

Agent status represents **human intent and presence**, not system load.

Supported statuses:

- AVAILABLE  
  The agent is online and willing to receive conversations.

- BUSY  
  The agent is currently handling at least one conversation but may still
  receive additional ones depending on capacity.

- PAUSED  
  The agent is temporarily unavailable (e.g., breaks, meetings).

- OFFLINE  
  The agent is not connected to the system.

Status changes are explicitly controlled by the agent or by authentication
events.

---

### Agent Capacity

Agent capacity represents the **maximum number of concurrent conversations**
an agent can handle.

- Capacity is configurable at the company level.
- Capacity may be extended in the future to support:
    - per-agent configuration
    - per-queue overrides
    - plan-based limits

---

### Active Conversation Count

Each agent maintains a runtime counter representing the number of
conversations currently in active attendance.

This counter is:
- Incremented when a conversation is assigned to the agent
- Decremented when a conversation is closed or transferred
- Evaluated in constant time (O(1))

The system must not calculate availability by counting conversations
on demand.

---

## Availability Rule

An agent is eligible to receive a new conversation if and only if:

1. The agent status is neither `OFFLINE` nor `PAUSED`
2. The number of active conversations is strictly less than the configured
   maximum capacity

Formally:

eligible = status ∉ {OFFLINE, PAUSED} AND activeConversations < maxConcurrentConversations

---

## Important Clarifications

- Reaching maximum capacity does **not** change agent status.
- Status must not be overloaded to represent capacity limits.
- Capacity exhaustion is a derived condition, not a persisted state.
- Dashboards may display capacity saturation without altering status.

---

## Performance Considerations

- Availability checks must be O(1).
- Active conversation count must be maintained incrementally.
- Realtime routing decisions must not execute aggregate queries.

---

## Notes

- This model aligns with industry-standard customer support platforms.
- The separation of status and capacity enables flexible routing strategies.
- Future enhancements (skills, prioritization, SLA) can build on this rule
  without refactoring existing logic.
