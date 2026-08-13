package io.github.omaralmayouf.food_delivery.restaurant.controller;

import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface RestaurantApi {

    String BASE_URL = "/restaurants";

    @Operation(
            summary = "Get all restaurants",
            responses = @ApiResponse(responseCode = "200", description = "OK")
    )
    @GetMapping
    ResponseEntity<List<RestaurantResponse>> getAllRestaurants();

    @Operation(
            summary = "Get restaurant by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<RestaurantResponse> getRestaurantById(
            @PathVariable("id") UUID restaurantId
    );

    @Operation(
            summary = "Create a new restaurant",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "422", description = "Cuisine not found")
            }
    )
    @PostMapping
    ResponseEntity<RestaurantResponse> createRestaurant(
            @RequestBody @Valid CreateRestaurantRequest request
    );
}
