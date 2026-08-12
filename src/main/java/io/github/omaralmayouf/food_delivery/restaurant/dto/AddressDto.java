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

        @NotBlank(message = "{error.address.city.required}")
        @Size(
                max = 50,
                message = "{error.address.city.too_long}"
        )
        String city,

        @NotBlank(message = "{error.address.district.required}")
        @Size(
                max = 50,
                message = "{error.address.district.too_long}"
        )
        String district,

        @NotBlank(message = "{error.address.street.required}")
        @Size(
                max = 100,
                message = "{error.address.street.too_long}"
        )
        String street,

        @NotNull(message = "{error.address.latitude.required}")
        @DecimalMin(
                value = "-90",
                message = "{error.address.latitude.min_value}"
        )
        @DecimalMax(
                value = "90",
                message = "{error.address.latitude.max_value}"
        )
        BigDecimal latitude,

        @NotNull(message = "{error.address.longitude.required}")
        @DecimalMin(
                value = "-180",
                message = "{error.address.longitude.min_value}"
        )
        @DecimalMax(
                value = "180",
                message = "{error.address.longitude.max_value}"
        )
        BigDecimal longitude) {
}
