package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class WorkingHoursDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static WorkingHoursDto.WorkingHoursDtoBuilder validWorkingHours() {
        return WorkingHoursDto.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(17, 0));
    }

    private List<Tuple> violationsOf(WorkingHoursDto workingHours) {
        return validator.validate(workingHours).stream()
                .map(violation -> tuple(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
    }

    @Test
    void shouldAcceptValidWorkingHours() {
        assertThat(validator.validate(validWorkingHours().build())).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6})
    void shouldAcceptEveryDayOfTheWeek(int dayOfWeek) {
        assertThat(validator.validate(validWorkingHours().dayOfWeek(dayOfWeek).build())).isEmpty();
    }

    @Test
    void shouldRejectMissingDayOfWeek() {
        assertThat(violationsOf(validWorkingHours().dayOfWeek(null).build()))
                .containsExactly(tuple("dayOfWeek", "{error.working_hours.day_of_week.required}"));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -99})
    void shouldRejectDayOfWeekBelowZero(int dayOfWeek) {
        assertThat(violationsOf(validWorkingHours().dayOfWeek(dayOfWeek).build()))
                .containsExactly(tuple("dayOfWeek", "{error.working_hours.day_of_week.min_value}"));
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 99})
    void shouldRejectDayOfWeekAboveSix(int dayOfWeek) {
        assertThat(violationsOf(validWorkingHours().dayOfWeek(dayOfWeek).build()))
                .containsExactly(tuple("dayOfWeek", "{error.working_hours.day_of_week.max_value}"));
    }

    @Test
    void shouldRejectMissingOpenTime() {
        assertThat(violationsOf(validWorkingHours().openTime(null).build()))
                .containsExactly(tuple("openTime", "{error.working_hours.open_time.required}"));
    }

    @Test
    void shouldRejectMissingCloseTime() {
        assertThat(violationsOf(validWorkingHours().closeTime(null).build()))
                .containsExactly(tuple("closeTime", "{error.working_hours.close_time.required}"));
    }

    @Test
    void shouldAcceptOvernightShiftWhereCloseTimeIsBeforeOpenTime() {
        WorkingHoursDto overnight = validWorkingHours()
                .openTime(LocalTime.of(23, 0))
                .closeTime(LocalTime.of(2, 0))
                .build();

        assertThat(validator.validate(overnight)).isEmpty();
    }

}
