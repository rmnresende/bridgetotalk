# Message Intake Rules

## Context

This document defines the decision-making process executed when the system
receives an inbound message from an external channel (e.g. WhatsApp, Telegram,
Web Chat).

This flow represents the core behavior of the BridgeToTalk platform and is
executed for every inbound message.

---

## Core Principles

1. No inbound message must be discarded.
2. Conversation context has priority over routing decisions.
3. Queue identification occurs only when creating a new conversation.
4. Existing conversations must never be rerouted automatically.

---

## Message Intake Flow

### 1. Identify Company and Channel

- Resolve the company based on the inbound channel configuration.
- Identify the channel type and external customer identifier
  (e.g. phone number, user ID, session ID).

---

### 1.5 Resolve Customer

The system must resolve the customer identity using:

- Company ID
- Channel type
- External customer identifier (e.g. phone number, user ID)

If no customer is found:

- A new customer must be automatically created.
- The customer is created with minimal required information.
- Customer enrichment may occur later in the conversation lifecycle.

---

### 2. Search for an Active Conversation

The system must search for an active conversation using:

- Company ID
- Channel type
- External customer identifier
- Conversation status ≠ CLOSED

---

### 3. Existing Conversation Found

If an active conversation is found:

- Reuse the existing conversation.
- Persist the inbound message linked to the conversation.
- Skip queue identification and routing logic.
- But, Check if the queue is currently open for new routing decisions.
- If queue is closed, set status to `OUT_OF_BUSINESS_HOURS` if current status is `WAITING_FOR_ATTENDANCE` and check if automated message for out business flow is enabled, if yes, send message to configured queue.
- If queue is open, keep the status current status (`IN_ATTENDANCE` or `WAITING_FOR_ATTENDANCE`), and continue the conversation lifecycle normally.

---

### 4. No Existing Conversation Found

If no active conversation is found:

- Check if exists a queue mapping configuration for the company and channel.
- If yes, resolve the target queue according to the Queue Identification Rules and send message to configured queue.  
- If no, send message to default queue **(every company must have a default queue)**.
- Create a new conversation using the resolved queue.
- Persist the inbound message linked to the conversation.
- Evaluate queue business hours.
- Set the initial conversation status:
    - `WAITING_FOR_ATTENDANCE` if the queue is open.
    - `OUT_OF_BUSINESS_HOURS` if the queue is closed.
- If Queue is closed, check if automated message for out business flow is enabled.
- If Queue is open, check if automated message for waiting flow is enabled.
- After, in open queue flow, check if exists a availble agent to handle the conversation.
- If exists, assign the agent to the conversation, set status to `IN_ATTENDANCE`.
- If no agent is available, set status to `WAITING_FOR_ATTENDANCE` and back to Open Queue validation step.

---

## Automated Messages

- Automated messages (welcome, off-hours, waiting) may be sent
  after message persistence.
- Automated messages do not affect conversation routing.

---

## Notes

- Message intake must be idempotent per external message identifier.
- Agent selection strategy details are out of scope for this document.
