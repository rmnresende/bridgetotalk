# Design Note – Skill-Based Routing

## Status
Exploration

## Date
2025-12-18

## Context

In customer service platforms, it is common to route conversations to
agents based on their skills and expertise. This allows conversations to
be handled by agents who are better prepared to resolve specific types
of issues.

In smaller companies, queues often implicitly represent a skill (e.g.
“Financial”, “Support”), making explicit skill modeling unnecessary.
However, in medium and larger companies, skills tend to be modeled as
independent concepts, often with proficiency levels.

BridgeToTalk currently routes conversations primarily based on queues,
agent availability and queue configuration, without an explicit skill
concept.

---

## Motivation

As the platform evolves to support more complex and realistic scenarios,
introducing an explicit skill model could:

- Improve routing quality
- Better reflect real-world support operations
- Allow more flexible queue and agent configurations
- Prepare the system for gradual growth without forcing complexity on
  smaller customers

At the same time, the solution must remain accessible and optional for
small and medium companies.

---

## Idea / Hypothesis

Introduce the concept of **skills** as an optional domain concept that can
influence conversation routing.

Key ideas under consideration:

- Agents may have zero or more skills
- Queues may require or prefer certain skills
- Skills may have proficiency levels (e.g. level 1, 2, 3)
- Routing may consider skill compatibility as a priority factor

The system must remain functional for companies that choose not to use
skills.

---

## Possible Approaches

### 1. Implicit Skills via Queues
Queues continue to represent skills implicitly.

- Simple
- No additional modeling
- Limited flexibility

---

### 2. Explicit Skill Entity (Optional)
Introduce a Skill concept that can be associated with agents and queues.

- Skills can be reused across queues
- Agents may have different proficiency levels per skill
- Queues may define required or preferred skills

---

### 3. Hybrid Approach
Support both implicit (queue-based) and explicit (skill-based) routing.

- Skills influence routing only when configured
- Default routing remains unchanged for simpler setups

---

## Trade-offs & Considerations

- Increased domain complexity
- Need to define clear routing precedence rules
- Potential overlap between queues and skills
- Risk of over-engineering for small customers
- Need for backward-compatible routing behavior

---

## Open Questions

- Should skills be company-wide or queue-scoped?
- Are skill levels mandatory or optional?
- How do skills interact with existing routing strategies?
- Should skills be strict requirements or soft preferences?
- How to handle routing when no agent fully matches required skills?

---

## Not a Decision

This document represents an exploration of the skill-based routing
concept.

No business rule, architectural decision or technical implementation has
been defined yet. Any future decision must be documented through a
dedicated ADR and corresponding business rules.
