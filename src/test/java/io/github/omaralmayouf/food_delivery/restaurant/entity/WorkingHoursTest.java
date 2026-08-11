package io.github.omaralmayouf.food_delivery.restaurant.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class WorkingHoursTest {

    @Test
    void shouldBeOpenWithinHours() {
        WorkingHours sundayShift = WorkingHours.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(17, 0))
                .build();

        LocalDateTime sundayNoon = LocalDateTime.of(2026, 8, 9, 12, 0);

        assertThat(sundayShift.covers(sundayNoon)).isTrue();
    }

    @Test
    void shouldBeOpenedExactlyAtOpeningTime() {
        WorkingHours sundayShift = WorkingHours.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(12, 0))
                .build();

        LocalDateTime sundayMorning = LocalDateTime.of(2026, 8, 9, 10, 0);

        assertThat(sundayShift.covers(sundayMorning)).isTrue();
        assertThat(sundayShift.covers(sundayMorning.minusMinutes(1))).isFalse();
    }

    @Test
    void shouldBeOpenAfterMidnightForOvernightShift() {
        WorkingHours sundayShift = WorkingHours.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(23, 0))
                .closeTime(LocalTime.of(3, 0))
                .build();

        LocalDateTime mondayMorning = LocalDateTime.of(2026, 8, 10, 1, 0);

        assertThat(sundayShift.covers(mondayMorning)).isTrue();
        assertThat(sundayShift.covers(mondayMorning.plusHours(1))).isTrue();
        assertThat(sundayShift.covers(mondayMorning.plusHours(2))).isFalse();
    }

    @Test
    void shouldBeClosedExactlyAtClosingTime() {
        WorkingHours sundayShift = WorkingHours.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(12, 0))
                .build();

        LocalDateTime sundayMorning = LocalDateTime.of(2026, 8, 9, 11, 0);

        assertThat(sundayShift.covers(sundayMorning)).isTrue();
        assertThat(sundayShift.covers(sundayMorning.plusHours(1))).isFalse();
    }

    @Test
    void shouldBeClosedOnADayWithNoShift() {
        WorkingHours sundayShift = WorkingHours.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(12, 0))
                .build();

        LocalDateTime anotherDay = LocalDateTime.of(2026, 8, 11, 0, 0);

        assertThat(sundayShift.covers(anotherDay)).isFalse();
    }

}
