package io.github.omaralmayouf.food_delivery.restaurant.service;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.request.CreateRestaurantRequest;
import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Cuisine;
import io.github.omaralmayouf.food_delivery.restaurant.exception.CuisineNotFoundException;
import io.github.omaralmayouf.food_delivery.restaurant.exception.RestaurantNotFoundException;
import io.github.omaralmayouf.food_delivery.restaurant.mapper.AddressMapper;
import io.github.omaralmayouf.food_delivery.restaurant.mapper.CuisineMapper;
import io.github.omaralmayouf.food_delivery.restaurant.mapper.RestaurantMapper;
import io.github.omaralmayouf.food_delivery.restaurant.mapper.WorkingHoursMapper;
import io.github.omaralmayouf.food_delivery.restaurant.repository.CuisineRepository;
import io.github.omaralmayouf.food_delivery.restaurant.repository.RestaurantRepository;
import io.github.omaralmayouf.food_delivery.shared.error.ErrorCode;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CuisineRepository cuisineRepository;

    private final WorkingHoursMapper workingHoursMapper = new WorkingHoursMapper();
    private final RestaurantMapper restaurantMapper =
            new RestaurantMapper(new AddressMapper(), new CuisineMapper(), workingHoursMapper);

    private RestaurantService service() {
        return new RestaurantService(restaurantRepository, cuisineRepository, restaurantMapper, workingHoursMapper);
    }

    private static CreateRestaurantRequest.CreateRestaurantRequestBuilder validRequest() {
        return CreateRestaurantRequest.builder()
                .name("restaurantA")
                .description("descriptionA")
                .address(AddressDto.builder()
                        .city("Riyadh")
                        .district("Olaya")
                        .street("King Fahd Road")
                        .latitude(new BigDecimal("24.713600"))
                        .longitude(new BigDecimal("46.675300"))
                        .build())
                .workingHours(List.of(
                        WorkingHoursDto.builder()
                                .dayOfWeek(0)
                                .openTime(LocalTime.of(9, 0))
                                .closeTime(LocalTime.of(23, 0))
                                .build(),
                        WorkingHoursDto.builder()
                                .dayOfWeek(1)
                                .openTime(LocalTime.of(9, 0))
                                .closeTime(LocalTime.of(23, 0))
                                .build()))
                .cuisineIds(List.of(1L, 2L));
    }

    private static Cuisine cuisine(long id, String name) {
        return Cuisine.builder().id(id).name(name).build();
    }

    @Test
    void shouldCreateRestaurantWithCuisinesAndWorkingHours() {
        when(cuisineRepository.findAllById(List.of(1L, 2L)))
                .thenReturn(List.of(cuisine(1L, "Burgers"), cuisine(2L, "Pizza")));

        RestaurantResponse response = service().createRestaurant(validRequest().build());

        verify(restaurantRepository).save(any());
        assertThat(response.name()).isEqualTo("restaurantA");
        assertThat(response.rating()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(response.workingHours()).hasSize(2);
        assertThat(response.cuisines())
                .extracting(c -> c.id())
                .containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void shouldRejectCreationWhenACuisineDoesNotExist() {
        when(cuisineRepository.findAllById(List.of(1L, 2L)))
                .thenReturn(List.of(cuisine(1L, "Burgers")));   // 2 requested, 1 found

        assertThatThrownBy(() -> service().createRestaurant(validRequest().build()))
                .isInstanceOf(CuisineNotFoundException.class)
                .satisfies(thrown -> {
                    CuisineNotFoundException exception = (CuisineNotFoundException) thrown;
                    assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.CUISINE_NOT_FOUND);
                    assertThat(exception.getMessageParams()).containsExactly(List.of(2L));
                });

        verify(restaurantRepository, never()).save(any());
    }

    @Test
    void shouldThrowNotFoundForUnknownRestaurantId() {
        UUID unknownId = UUID.randomUUID();
        when(restaurantRepository.findById(unknownId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service().getRestaurantById(unknownId))
                .isInstanceOf(RestaurantNotFoundException.class);
    }

}
