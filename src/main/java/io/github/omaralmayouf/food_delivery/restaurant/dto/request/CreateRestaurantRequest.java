package io.github.omaralmayouf.food_delivery.restaurant.dto.request;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;

import org.hibernate.validator.constraints.URL;

import java.util.List;

@Builder
public record CreateRestaurantRequest(

        @NotBlank(message = "{error.restaurant.name.required}")
        @Size(
                max = 50,
                message = "{error.restaurant.name.too_long}"
        )
        String name,

        @Size(
                max = 160,
                message = "{error.restaurant.description.too_long}"
        )
        String description,

        @URL(message = "{error.restaurant.logo_url.not_valid}")
        String logoUrl,

        @Valid
        @NotEmpty(message = "{error.restaurant.working_hours.required}")
        List<WorkingHoursDto> workingHours,

        @Valid
        @NotNull(message = "{error.restaurant.address.required}")
        AddressDto address,

        @NotEmpty(message = "{error.restaurant.cuisines.required}")
        List<Long> cuisineIds) {
}
