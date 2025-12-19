# Agent Availability Rules

## Context
Agent availability determines whether an agent can participate in customer
attendance and be assigned to queues.

## Core Rules

1. An agent has exactly one status at any given time.
2. Supported statuses include:
    - AVAILABLE
    - BUSY
    - PAUSED
    - OFFLINE
3. An agent must be **AVAILABLE** to be linked to a queue.
4. Status transitions must result in a state change.
5. Updating an agent to the same status is considered invalid.

## Notes
- Availability rules are enforced by the Agent domain.
- Status changes update the agent’s last modified timestamp.
