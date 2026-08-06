package io.github.omaralmayouf.food_delivery.restaurant.dto;

import lombok.Builder;

@Builder
public record CuisineDto(

        Long id,

        String name) {
}
