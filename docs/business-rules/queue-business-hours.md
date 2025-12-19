# Queue Business Hours

## Context
Queues define when customer attendance is allowed. Business hours must be
flexible enough to support different schedules per weekday, including days
without attendance.

## Core Rules

1. A queue is considered **open for attendance** only during its configured
   business hours.
2. Days without a configured schedule are considered **closed days**.
3. Each day may have **one or more time ranges**.
4. Time ranges **must not overlap**.
5. The start time of a range is **inclusive**.
6. The end time of a range is **exclusive**.
7. Weekends are considered closed by default unless explicitly configured.

## Examples

### Open day
- Monday: 09:00–18:00

### Split schedule
- Tuesday: 08:00–12:00 and 13:00–18:00

### Closed day
- Saturday: no schedule → closed

## Notes
- Business hours evaluation is always performed by the Queue domain.
- Future extensions may include holidays and date-based exceptions.
