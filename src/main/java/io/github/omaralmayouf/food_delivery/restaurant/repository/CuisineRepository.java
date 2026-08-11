package io.github.omaralmayouf.food_delivery.restaurant.repository;

import io.github.omaralmayouf.food_delivery.restaurant.entity.Cuisine;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CuisineRepository extends JpaRepository<Cuisine, Long> {
}
