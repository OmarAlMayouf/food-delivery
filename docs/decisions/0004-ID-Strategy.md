# ID Strategy

## Status

Accepted

## Context

How should we generate IDs? Should we use UUIDs? Should we use sequential IDs?
These are all valid options but has tradeoffs.
So for the restaurant entity, we decided to use UUIDs, and for the cuisine entity,
we decided to use sequential IDs.

## Decision

We will use UUIDs for the restaurant entity and sequential IDs for the cuisine entity.
Why UUIDs? Because they are globally unique, and sequential IDs are easy to guess
which leaves us with vulnerability issues such as guessing the next ID.
UUIDs just make IDs hard(er) to guess they are not an authorization check.
Every endpoint must still verify the caller is allowed to access the resource.
For Ids competitors will be able to guess the number of restaurants in the system.
Therefore, we will use UUIDs for the restaurant entity.
So that leaves us with the next question: what version of UUIDs should we use?
We will use version 7, that's because it has better index locality and has timestamps while 4 is completely random.

As for the cuisine entity, we will use sequential IDs. Competitors will be able to guess the number of cuisines
and in the system, and that is not an issue, and it has no real security implications.

## Consequences

Consequences of uuids are that they have a size of 16 bytes instead of 8 bytes.
This is not a big deal, but it is something to keep in mind as the system grows.

Another thing to keep in mind is that the UUID's are usually harder to debug than sequential IDs.
