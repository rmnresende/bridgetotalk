# Conversation Transfer Flow

## Context

This document describes the rules and operational flow for transferring an
existing conversation between queues.

Conversation transfer is a **deliberate routing action** that occurs during
the conversation lifecycle and must never affect message history, customer
identity, or conversation continuity.

Transfers may be initiated manually by agents or supervisors, or automatically
by explicit business rules defined by the system.

---

## Core Principles

1. Conversation transfers are **explicit actions**, never implicit or automatic
   during message intake.

2. A conversation always has **one active queue**, representing its current
   routing context.

3. All queue transfers must be **fully auditable**.

4. Transfers do not reset:
    - Conversation identity
    - Message history
    - Customer context
    - Conversation lifecycle

5. The current queue may change, but the conversation remains the same
   aggregate.

---

## Preconditions for Transfer

A conversation may be transferred only if:

- The conversation status is **not `CLOSED`**.
- The target queue belongs to the **same company**.
- The target queue is **active** (not deleted).
- The transfer action is performed by:
    - An assigned agent, or
    - A supervisor or system process with sufficient privileges.

---

## Transfer Flow

### 1. Transfer Initiation

A transfer may be initiated by:

- An agent during an active conversation.
- A supervisor via administrative action.
- An automated rule explicitly defined by the system (future).

The transfer request must include:
- Conversation ID
- Target Queue ID
- Initiator context (agent, system, supervisor)

---

### 2. Validation

Before executing the transfer, the system must validate:

- The conversation exists and is active.
- The target queue exists and belongs to the same company.
- The target queue is different from the current queue.

If validation fails, the transfer must be rejected.

---

### 3. Persist Queue Transfer History

Before updating the active queue:

- Persist a **queue transfer record** containing:
    - Conversation ID
    - Source Queue ID
    - Target Queue ID
    - Timestamp
    - Initiator (agent/system)
    - Optional reason or note

This record represents the **immutable routing history** of the conversation.

---

### 4. Update Active Queue

After recording the transfer history:

- Update the conversation’s active queue to the target queue.
- Update the conversation’s `updatedAt` timestamp.

---

### 5. Post-Transfer Evaluation

After the transfer:

- Evaluate target queue business hours.
- If the target queue is **open**:
    - Set conversation status to `WAITING_FOR_ATTENDANCE`.
    - Trigger routing logic for agent assignment.
- If the target queue is **closed**:
    - Set conversation status to `OUT_OF_BUSINESS_HOURS`.
    - Optionally send automated off-hours message.

---

## Status Handling Rules

- Transfers **do not close** conversations.
- Transfers **do not reset** message history.
- Transfers may change conversation status only as a consequence of:
    - Queue business hours
    - Agent assignment rules

---

## Queue History Model (Conceptual)

A conversation maintains:

- One **active queue**
- Zero or more **historical queue associations**

The latest queue in history is always the active queue.

---

## Notes

- Message intake must always respect the conversation’s current active queue.
- Transfers must never be inferred implicitly from messages or time conditions.
- Queue reassignment logic must remain decoupled from message persistence.
- Future enhancements may include:
    - Transfer reasons
    - SLA recalculation
    - Skill-based re-routing

