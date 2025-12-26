# Queue Identification Rules

## Context

Inbound messages received by BridgeToTalk originate from external channels
without internal routing context.

Queue identification is required only when creating a new conversation.

This document defines how the system determines the target queue for a
conversation during its creation.

---

## Core Principles

1. Queue identification is configuration-driven.
2. Queue identification is executed only once per conversation.
3. No automatic queue reassignment occurs after conversation creation.
4. **A default queue must always exist per company.**

---

## Channel-to-Queue Mapping

Each external channel entry point may be explicitly associated with a queue.

Examples:
- WhatsApp number +55 11 99999-0001 → Sales Queue
- Telegram Bot @company_support_bot → Support Queue

If a channel-to-queue mapping exists, the mapped queue must be used.

---

## Default Queue Fallback

If no channel-to-queue mapping exists:

- The conversation must be created using the company's default queue.
- The default queue represents a general intake point.

---

## Queue Transfer

After a conversation is created:

- Agents may manually transfer the conversation to another queue.
- Queue transfer does not affect conversation history.
- Queue transfer rules are evaluated independently from message intake.

---

## Notes

- Automatic routing mechanisms (IVR, keyword-based, NLP) are intentionally
  out of scope for the initial implementation.
- This model prioritizes simplicity and predictability for small and
  medium-sized companies.
