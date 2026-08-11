package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Restaurant;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantMapperTest {

    private final CuisineMapper cuisineMapper = new CuisineMapper();
    private final AddressMapper addressMapper = new AddressMapper();
    private final WorkingHoursMapper workingHoursMapper = new WorkingHoursMapper();

    private final RestaurantMapper restaurantMapper = new RestaurantMapper(addressMapper, cuisineMapper, workingHoursMapper);

    private static final LocalDateTime SUNDAY_NOON = LocalDateTime.of(2026, 8, 9, 12, 0);   // Sunday

    AddressDto mockAddress = AddressDto
            .builder()
            .city("city")
            .district("district")
            .street("street")
            .latitude(BigDecimal.ONE)
            .longitude(BigDecimal.ONE)
            .build();

    @Test
    void shouldReturnRestaurantResponseFromEntity() {
        Restaurant restaurant = Restaurant
                .builder()
                .id(UUID.randomUUID())
                .name("restaurantA")
                .description("descriptionA")
                .logoUrl(null)
                .rating(BigDecimal.ONE)
                .address(addressMapper.toEntityFromDto(mockAddress))
                .manuallyPaused(false)
                .build();

        RestaurantResponse response = restaurantMapper.toDtoFromEntity(restaurant, SUNDAY_NOON);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(restaurant.getId());
        assertThat(response.name()).isEqualTo(restaurant.getName());
        assertThat(response.description()).isEqualTo(restaurant.getDescription());
        assertThat(response.logoUrl()).isNull();
        assertThat(response.rating()).isEqualTo(restaurant.getRating());
        assertThat(response.address()).isEqualTo(mockAddress);
        assertThat(response.workingHours()).isEmpty();
        assertThat(response.cuisines()).isEmpty();
        assertThat(response.acceptingOrders()).isFalse();
    }

    @Test
    void shouldReturnRestaurantEntityFromRequest() {

        WorkingHoursDto workingHoursDto1 = WorkingHoursDto
                .builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        WorkingHoursDto workingHoursDto2 = WorkingHoursDto
                .builder()
                .dayOfWeek(1)
                .openTime(LocalTime.of(7, 0))
                .closeTime(LocalTime.of(20, 0))
                .build();

        List<WorkingHoursDto> workingHoursDtoList = List.of(workingHoursDto1, workingHoursDto2);

        List<Long> cuisineIds = List.of(1L, 2L);

        CreateRestaurantRequest request = CreateRestaurantRequest
                .builder()
                .name("restaurantA")
                .description("descriptionA")
                .logoUrl(null)
                .address(mockAddress)
                .workingHours(workingHoursDtoList)
                .cuisineIds(cuisineIds)
                .build();

        Restaurant restaurant = restaurantMapper.toEntityFromRequest(request);

        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getName()).isEqualTo(request.name());
        assertThat(restaurant.getDescription()).isEqualTo(request.description());
        assertThat(restaurant.getLogoUrl()).isNull();
        assertThat(restaurant.getRating()).isEqualByComparingTo(BigDecimal.ZERO);

        assertThat(restaurant.getAddress().getCity()).isEqualTo("city");
        assertThat(restaurant.getAddress().getDistrict()).isEqualTo("district");
        assertThat(restaurant.getAddress().getStreet()).isEqualTo("street");
        assertThat(restaurant.getAddress().getLatitude()).isEqualByComparingTo("1");
        assertThat(restaurant.getAddress().getLongitude()).isEqualByComparingTo("1");

        assertThat(restaurant.getWorkingHours()).isEmpty();
        assertThat(restaurant.getCuisines()).isEmpty();
        assertThat(restaurant.isAcceptingOrders(SUNDAY_NOON)).isFalse();
        assertThat(restaurant.isManuallyPaused()).isFalse();
    }
}
