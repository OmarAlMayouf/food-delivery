The Restaurant module is responsible for managing restaurant information, ownership, and opening hours.



restaurant information includes :

- Name

- Description

- Logo

- Address / Location

- Cuisine Type

- Rating (read-only)



It of course includes basic create, update and delete a restaurant :



update includes : opening hours, every thing in restaurant information EXCEPT no update of rating since it will be read from customers it will always start form 0



Ownership

- A restaurant is owned by exactly one restaurant owner.

- One restaurant owner may own multiple restaurants.

Availability

- Closed restaurants cannot receive new orders.

- Customers can browse closed restaurants but cannot place orders.

Identity

- Restaurant names are not required to be unique.

- Restaurants are uniquely identified by an ID.

MVP Decision

- Each physical branch is represented as a separate restaurant.

- Restaurants may be created without menu items.