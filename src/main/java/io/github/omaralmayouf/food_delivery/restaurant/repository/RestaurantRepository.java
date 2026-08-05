package io.github.omaralmayouf.food_delivery.restaurant.repository;

import io.github.omaralmayouf.food_delivery.restaurant.entity.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    List<Restaurant> findByAddressCity(String city);
    
}
