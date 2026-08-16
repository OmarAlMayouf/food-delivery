package io.github.omaralmayouf.food_delivery.restaurant.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantTest {

    private static final LocalDateTime SUNDAY_NOON = LocalDateTime.of(2026, 8, 9, 12, 0);
    private static final LocalDateTime SUNDAY_NIGHT = LocalDateTime.of(2026, 8, 9, 22, 0);

    private WorkingHours sundayNineToFive() {
        return WorkingHours.builder()
                .id(UUID.randomUUID())
                .dayOfWeek(0)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(17, 0))
                .build();
    }

    private Restaurant restaurantWithoutWorkingHours() {
        return Restaurant.builder()
                .id(UUID.randomUUID())
                .name("restaurantA")
                .description("descriptionA")
                .rating(BigDecimal.ZERO)
                .manuallyPaused(false)
                .build();
    }

    private Restaurant restaurantOpenOnSunday() {
        Restaurant restaurant = restaurantWithoutWorkingHours();
        restaurant.addWorkingHours(sundayNineToFive());
        return restaurant;
    }

    @Test
    void shouldLinkBothSidesWhenAddingWorkingHours() {
        Restaurant restaurant = restaurantWithoutWorkingHours();
        WorkingHours hours = sundayNineToFive();

        restaurant.addWorkingHours(hours);

        assertThat(restaurant.getWorkingHours()).containsExactly(hours);
        assertThat(hours.getRestaurant()).isSameAs(restaurant);
    }

    @Test
    void shouldAcceptOrdersWithinWorkingHoursWhenNotPaused() {
        assertThat(restaurantOpenOnSunday().isAcceptingOrders(SUNDAY_NOON)).isTrue();
    }

    @Test
    void shouldNotAcceptOrdersOutsideWorkingHours() {
        assertThat(restaurantOpenOnSunday().isAcceptingOrders(SUNDAY_NIGHT)).isFalse();
    }

    @Test
    void shouldNotAcceptOrdersWhenManuallyPausedEvenWithinWorkingHours() {
        Restaurant restaurant = restaurantOpenOnSunday();
        restaurant.setManuallyPaused(true);

        assertThat(restaurant.isAcceptingOrders(SUNDAY_NOON)).isFalse();
    }

    @Test
    void shouldNotAcceptOrdersWhenNoWorkingHoursDefined() {
        assertThat(restaurantWithoutWorkingHours().isAcceptingOrders(SUNDAY_NOON)).isFalse();
    }

    @Test
    void shouldKeepMultipleUnsavedWorkingHours() {
        Restaurant restaurant = restaurantWithoutWorkingHours();

        WorkingHours sundayShift = WorkingHours.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(17, 0))
                .build();

        WorkingHours mondayShift = WorkingHours.builder()
                .dayOfWeek(1)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        restaurant.addWorkingHours(sundayShift);
        restaurant.addWorkingHours(mondayShift);

        assertThat(restaurant.getWorkingHours()).containsExactlyInAnyOrder(sundayShift, mondayShift);
    }

}
