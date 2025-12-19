# Request Flow

## Purpose

This document describes how a request flows through the BridgeToTalk
system, from the moment it enters the application until it reaches the
domain and returns a response.

The goal is to make architectural boundaries explicit and demonstrate how
Hexagonal Architecture is applied in practice.

---

## General Flow Overview

All requests in BridgeToTalk follow the same high-level flow:

Inbound Adapter (HTTP)
↓
Application Layer (Use Case)
↓
Domain Logic
↓
Outbound Adapter (Persistence / Infrastructure)


### Key principles:
- Controllers do not contain business logic
- Use cases orchestrate, but do not model rules
- Domain entities enforce invariants
- Adapters translate between the core and the outside world

---

## Example 1 – Link Agent to Queue

### Scenario

An agent is associated with a queue so they can receive conversations.

This operation involves:
- Validating agent existence and status
- Validating queue existence
- Ensuring both belong to the same company
- Creating the association

---

### Step-by-Step Flow

```
POST /queues/{queueId}/agents/{agentId}
```


1. **Inbound Adapter (Controller)**
    - Receives HTTP request
    - Validates request format
    - Maps input into `LinkQueueAgentCommand`
    - Calls the application use case

2. **Application Layer (Use Case)**
    - Coordinates the operation
    - Loads required domain objects through ports
    - Delegates rule enforcement to the domain
    - Calls the repository port to persist the association

3. **Domain Layer**
    - Validates business rules:
        - Agent must be AVAILABLE
        - Agent and Queue must belong to the same company
    - No knowledge of HTTP, persistence or frameworks

4. **Outbound Adapter**
    - Implements repository ports
    - Persists the agent–queue association
    - Uses JPA or another persistence mechanism

5. **Response**
    - Control returns to the controller
    - HTTP response is generated (e.g. 204 No Content)

---

### Visual Flow

Controller
↓
LinkAgentToQueueUseCase
↓
Agent / Queue (Domain Rules)
↓
AgentQueueRepositoryPort
↓
JPA Adapter


---

## Example 2 – Check Queue Business Hours

### Scenario

The system must determine whether a queue is open for attendance at a
given moment.

---

### Step-by-Step Flow

1. **Application Layer**
    - Requests queue state
    - Delegates the evaluation to the domain

2. **Domain Layer**
    - `Queue` delegates to `WeeklySchedule`
    - `WeeklySchedule` resolves the current weekday
    - `DailySchedule` checks configured time ranges
    - Returns whether the queue is open or closed

3. **Application Layer**
    - Uses the result to decide:
        - Start a conversation
        - Send an automated message
        - Reject routing

---

### Visual Flow

Use Case
↓
Queue
↓
WeeklySchedule
↓
DailySchedule
↓
TimeRange


---

## Responsibilities by Layer

### Inbound Adapters
- Input validation
- DTO mapping
- Protocol-specific concerns (HTTP)

### Application Layer
- Orchestration of use cases
- Coordination between subdomains
- Transaction boundaries

### Domain Layer
- Business rules
- Invariants
- State transitions

### Outbound Adapters
- Persistence
- External integrations
- Technical implementations of ports

---

## Error Propagation

- Domain exceptions represent invalid states or rule violations
- Application layer does not swallow domain exceptions
- Inbound adapters translate exceptions into HTTP responses

This behavior follows the rules defined in **ADR-004 – Domain Exceptions Strategy**.

---

## What This Flow Explicitly Avoids

- Controllers accessing repositories directly
- Business rules implemented in adapters
- Framework annotations in the domain
- Implicit orchestration logic

Violations of these rules indicate architectural erosion.

---

## Notes

This document illustrates common flows but is not exhaustive. As new use
cases are introduced, they should follow the same architectural pattern
unless explicitly documented otherwise through ADRs.
