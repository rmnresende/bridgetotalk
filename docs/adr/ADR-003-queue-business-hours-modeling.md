# ADR-003 – Queue Business Hours Modeling

## Status
Accepted

## Date
2025-12-19

## Context

Queues in BridgeToTalk define when customer attendance is allowed. Business
hours are a core part of the domain and directly impact message routing,
conversation creation, automated responses, and agent assignment.

Business hours requirements include:
- Different schedules per weekday
- Days without attendance
- Support for multiple time ranges in a single day
- Clear and deterministic evaluation rules

A simplistic model (single start/end fields or flags) would not support
real-world scenarios and would quickly become a limitation as the domain
evolves.

## Decision

Business hours are modeled using a hierarchy of domain objects:

- **WeeklySchedule**
- **DailySchedule**
- **TimeRange**

The rules are defined as follows:

1. Business hours are evaluated per weekday.
2. Days without a configured schedule are considered **closed**.
3. Each day may contain one or more time ranges.
4. Time ranges must not overlap.
5. Start time is inclusive.
6. End time is exclusive.

The Queue aggregate exposes a single business-level operation
(`isOpenForAttendance`) to determine whether attendance is allowed at a
given moment.

## Alternatives Considered

### 1. Single Start and End Fields on Queue

Example:
```text
openTime
closeTime
```

### Rejected because:

- Cannot represent split schedules (e.g. lunch breaks)

- Cannot represent closed days explicitly

- Leads to conditional logic scattered across the application

### 2. JSON-Based Schedule Configuration

Storing schedules as a generic JSON structure.

### Rejected because:

Lacks type safety

- Weak validation guarantees

- Business rules become implicit rather than explicit

- Harder to test and reason about


### 3. Hardcoded Weekday Logic

Assuming fixed business days (e.g. Monday to Friday).

### Rejected because:

- Not flexible enough for real-world use

- Violates the principle of explicit configuration

- Prevents future extensions

## Consequences

### Positive

- Clear and expressive domain model

- Business rules are explicit and centralized

- Flexible representation of real-world schedules

- Easy to extend for future requirements (holidays, exceptions, timezones)

- Reduced risk of subtle bugs related to time evaluation

### Negative

- Increased modeling complexity

- More domain objects than a simplistic approach

- Slightly higher learning curve for new contributors

These trade-offs are considered acceptable due to the critical nature of
business hours in the system.

###  Notes

- Business hours evaluation is performed exclusively by the Queue domain.

- Infrastructure concerns such as persistence and timezone handling are
intentionally excluded from this decision and may be addressed in
future ADRs.