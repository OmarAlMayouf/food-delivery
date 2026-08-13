package io.github.omaralmayouf.food_delivery.restaurant.service;

import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Cuisine;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Restaurant;
import io.github.omaralmayouf.food_delivery.restaurant.entity.WorkingHours;
import io.github.omaralmayouf.food_delivery.restaurant.exception.CuisineNotFoundException;
import io.github.omaralmayouf.food_delivery.restaurant.exception.RestaurantNotFoundException;
import io.github.omaralmayouf.food_delivery.restaurant.mapper.RestaurantMapper;
import io.github.omaralmayouf.food_delivery.restaurant.mapper.WorkingHoursMapper;
import io.github.omaralmayouf.food_delivery.restaurant.repository.CuisineRepository;
import io.github.omaralmayouf.food_delivery.restaurant.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RestaurantService {

    final RestaurantRepository restaurantRepository;
    final CuisineRepository cuisineRepository;

    final RestaurantMapper restaurantMapper;
    final WorkingHoursMapper workingHoursMapper;

    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurant -> restaurantMapper.toDtoFromEntity(restaurant, LocalDateTime.now(ZoneId.of("Asia/Riyadh"))))
                .toList();
    }

    public RestaurantResponse getRestaurantById(UUID restaurantId) {
        return restaurantMapper.toDtoFromEntity(
                restaurantRepository
                        .findById(restaurantId)
                        .orElseThrow(
                                () -> new RestaurantNotFoundException(restaurantId)
                        ),
                LocalDateTime.now(ZoneId.of("Asia/Riyadh"))
        );
    }

    public RestaurantResponse createRestaurant(CreateRestaurantRequest request) {

        Restaurant restaurant = restaurantMapper.toEntityFromRequest(request);

        // assign working hours
        List<WorkingHours> workingHours = workingHoursMapper.toEntityListFromDtoList(request.workingHours());
        workingHours.forEach(restaurant::addWorkingHours);

        // assign cuisines
        List<Cuisine> cuisines = cuisineRepository.findAllById(request.cuisineIds());

        // check if all cuisines are found
        Set<Long> foundIds = cuisines.stream().map(Cuisine::getId).collect(Collectors.toSet());
        List<Long> missingIds = request.cuisineIds()
                .stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) throw new CuisineNotFoundException(missingIds);

        restaurant.setCuisines(new HashSet<>(cuisines));

        // save entities
        restaurantRepository.save(restaurant);

        return restaurantMapper.toDtoFromEntity(restaurant, LocalDateTime.now(ZoneId.of("Asia/Riyadh")));
    }

}
