package io.github.omaralmayouf.food_delivery.restaurant.controller;

import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.service.RestaurantService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RestaurantController {

    final RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable("id") UUID restaurantId
    ) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> createRestaurant(
            @RequestBody @Valid CreateRestaurantRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(request));
    }

}
