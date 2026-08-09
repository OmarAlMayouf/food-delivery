# Module Boundaries

## Status

Proposed

## Context

This Proposal is about module boundaries.
It is not about the structure of the code modular monolith (ADR-0002),
instead it is about the boundaries of the modules. So what is meant here is that
the modules should not depend on other modules. For example the `PaymentService`
should not depend on the `OrderRepository`. They should only communicate via
the same module if they are in the same module or via the `Service` Layer instead.

And since java packages aren't hierarchical, so every class crossing those sub-packages must be public.
That is what removed the compiler's ability to enforce our boundaries and why enforcement now needs a tool.

## Decision

This Project will use the `Service` Layer to communicate between modules.
And shall not allow any other module to depend on the inner layers of other modules.
That's makes the code more testable, easier to understand, to maintain,
and protects the implementation (business logic) code from external changes.

How should we enforce this? There's no specific ONLY correct way to this.
I can either :

- use the Spring Modulith (which is the best for me, since the project is already using the Modulith
  structure)
- use ArchUnit, writing custom rules for the project just like tests
- depend on myself or documentation and best practices, which is not a good practice (no enforcing here)

so I will use the Modulith Spring Modulith dependency for this.

## Consequences

Until Modulith lands, boundaries are convention only, enforced by discipline.
