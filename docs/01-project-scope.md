# Project Goals

This project is built for learning backend engineering and software architecture.

1. Learn Spring Boot deeply
2. Understand software architecture
3. Build a production-like backend
4. Learn modular monolith architecture
5. Later migrate to microservices
6. Apply clean code and testing practices

# Project Scope
Food delivery full stack application that allows users to order food from restaurants.

business flow : 
1. User opens the app
2. user browse restaurants
3. user picks a restaurant
4. user browses the menu
5. user add items to cart
6. user checkout
7. user pay
8. restaurant accept the order then receives payment from the platform later
9. driver is assigned to order
10. driver delivers food
11. order is delivered
12. order tracking
13. user and driver gets live notifications

Current MVP:
- restaurant browsing
- restaurant management
- menu browsing
- shopping cart
- order placement
- driver assignment
- order tracking
- notifications
- authentication
- authorizations
- payment gateway

not in MVP:
- coupons
- multiple restaurant checkout
- live GPS tracking
- reviews and ratings

## Roles: 
- Restaurant owner who manages the menu and stock 
- Customer who orders food from restaurants 
- Driver who delivers food to customers 
- Platform admin who manages the platform

## Assumptions
- One customer order can only belong to one restaurant. 
- One restaurant owner may own multiple restaurants. 
- Orders are paid online. 
- Driver assignment is automatic. 
- Restaurants can reject incoming orders. 
- Closed restaurants cannot be ordered from.
- Out-of-stock items cannot be added to the cart.
