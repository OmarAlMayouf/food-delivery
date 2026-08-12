package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record WorkingHoursDto(

        // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
        @NotNull(message = "{error.working_hours.day_of_week.required}")
        @Min(value = 0, message = "{error.working_hours.day_of_week.min_value}")
        @Max(value = 6, message = "{error.working_hours.day_of_week.max_value}")
        Integer dayOfWeek,

        @NotNull(message = "{error.working_hours.open_time.required}")
        LocalTime openTime,

        @NotNull(message = "{error.working_hours.close_time.required}")
        LocalTime closeTime
) {
}
