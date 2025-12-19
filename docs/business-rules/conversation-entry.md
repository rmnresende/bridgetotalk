# Conversation Entry Rules

## Context
A conversation represents the lifecycle of communication between a customer
and the system, routed through a queue.

## Core Rules

1. A conversation can only be initiated if the queue is **open for attendance**.
2. If a message arrives outside business hours:
    - The conversation is not routed to an agent.
    - An off-hours automated message may be sent.
3. Queue business hours are the single source of truth for attendance eligibility.

## Notes
- Conversation routing rules may evolve independently from queue configuration.
- Message handling behavior may differ based on future channel support.
