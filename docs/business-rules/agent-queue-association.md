# Agent–Queue Association Rules

## Context
Agents can be associated with one or more queues to receive conversations.
This association is governed by operational rules to ensure consistency.

## Core Rules

1. An agent can only be linked to a queue that belongs to the **same company**.
2. An agent must be in **AVAILABLE** status to be linked to a queue.
3. An agent can be unlinked from a queue regardless of its current status.
4. The same agent cannot be linked to the same queue more than once.
5. Agent–queue associations are explicitly created and removed.

## Notes
- Availability is checked only during link operations.
- Unlinking an agent does not affect the agent’s status.
- Queue ownership validation is mandatory for both link and unlink operations.
