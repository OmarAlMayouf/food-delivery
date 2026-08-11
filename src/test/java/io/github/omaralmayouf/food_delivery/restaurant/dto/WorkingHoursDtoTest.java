package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class WorkingHoursDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private WorkingHoursDto.WorkingHoursDtoBuilder validWorkingHours() {
        return WorkingHoursDto.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(17, 0));
    }

    private Set<String> rejectedFieldsOf(WorkingHoursDto workingHours) {
        return validator.validate(workingHours).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
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

    @ParameterizedTest
    @ValueSource(ints = {-1, 7, 99})
    void shouldRejectDayOfWeekOutsideZeroToSix(int dayOfWeek) {
        assertThat(rejectedFieldsOf(validWorkingHours().dayOfWeek(dayOfWeek).build()))
                .contains("dayOfWeek");
    }

    @Test
    void shouldRejectMissingDayOfWeek() {
        assertThat(rejectedFieldsOf(validWorkingHours().dayOfWeek(null).build()))
                .contains("dayOfWeek");
    }

    @Test
    void shouldRejectMissingOpenTime() {
        assertThat(rejectedFieldsOf(validWorkingHours().openTime(null).build()))
                .contains("openTime");
    }

    @Test
    void shouldRejectMissingCloseTime() {
        assertThat(rejectedFieldsOf(validWorkingHours().closeTime(null).build()))
                .contains("closeTime");
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
