package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AddressDto(

        @NotBlank(message = "City is required")
        @Size(
                max = 50,
                message = "City is too long, must be at most 50 characters"
        )
        String city,

        @NotBlank(message = "District is required")
        @Size(
                max = 50,
                message = "District is too long, must be at most 50 characters"
        )
        String district,

        @NotBlank(message = "Street is required")
        @Size(
                max = 100,
                message = "Street is too long, must be at most 100 characters"
        )
        String street,

        @NotNull(message = "Latitude is required")
        @DecimalMin(
                value = "-90",
                message = "Latitude must be at least -90"
        )
        @DecimalMax(
                value = "90",
                message = "Latitude must be at most 90"
        )
        BigDecimal latitude,

        @NotNull(message = "Longitude is required")
        @DecimalMin(
                value = "-180",
                message = "Longitude must be at least -180"
        )
        @DecimalMax(
                value = "180",
                message = "Longitude must be at most 180"
        )
        BigDecimal longitude) {
}
