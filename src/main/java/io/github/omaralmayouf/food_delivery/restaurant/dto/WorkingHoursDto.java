package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record WorkingHoursDto(

        // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
        @NotNull(message = "Day of week is required")
        @Min(value = 0, message = "Day of week must be minimum 0")
        @Max(value = 6, message = "Day of week must be at most 6")
        Integer dayOfWeek,

        @NotNull(message = "Open time is required")
        LocalTime openTime,

        @NotNull(message = "Close time is required")
        LocalTime closeTime
) {
}
