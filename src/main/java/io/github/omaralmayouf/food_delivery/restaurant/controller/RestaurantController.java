package io.github.omaralmayouf.food_delivery.restaurant.controller;

import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.service.RestaurantService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static io.github.omaralmayouf.food_delivery.restaurant.controller.RestaurantApi.BASE_URL;


@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RestaurantController implements RestaurantApi {

    final RestaurantService restaurantService;

    @Override
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurants());
    }

    @Override
    public ResponseEntity<RestaurantResponse> getRestaurantById(UUID restaurantId) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    @Override
    public ResponseEntity<RestaurantResponse> createRestaurant(CreateRestaurantRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.createRestaurant(request));
    }

}
