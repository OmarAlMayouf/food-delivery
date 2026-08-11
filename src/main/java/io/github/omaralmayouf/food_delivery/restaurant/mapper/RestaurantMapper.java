package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Restaurant;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RestaurantMapper {

    final AddressMapper addressMapper;
    final CuisineMapper cuisineMapper;
    final WorkingHoursMapper workingHoursMapper;

    public RestaurantResponse toDtoFromEntity(Restaurant restaurant) {
        return RestaurantResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .description(restaurant.getDescription())
                .logoUrl(restaurant.getLogoUrl())
                .rating(restaurant.getRating())
                .address(addressMapper.toDtoFromEntity(restaurant.getAddress()))
                .workingHours(workingHoursMapper.toDtoListFromEntitySet(restaurant.getWorkingHours()))
                .cuisines(cuisineMapper.toDtoListFromEntitySet(restaurant.getCuisines()))
                .acceptingOrders(restaurant.isAcceptingOrders())
                .build();
    }

    public Restaurant toEntityFromRequest(CreateRestaurantRequest request) {
        // Working hours and cuisines are not set here, they are set in the service
        return Restaurant.builder()
                .name(request.name())
                .description(request.description())
                .logoUrl(request.logoUrl())
                .rating(BigDecimal.ZERO)
                .address(addressMapper.toEntityFromDto(request.address()))
                .manuallyPaused(false)
                .build();
    }

}
