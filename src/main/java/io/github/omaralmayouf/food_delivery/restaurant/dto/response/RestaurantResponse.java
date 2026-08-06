package io.github.omaralmayouf.food_delivery.restaurant.dto.response;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.CuisineDto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
public record RestaurantResponse(

        UUID id,

        String name,

        String description,

        String logoUrl,

        AddressDto address,

        BigDecimal rating,

        List<CuisineDto> cuisines,

        boolean acceptingOrders) {
}
