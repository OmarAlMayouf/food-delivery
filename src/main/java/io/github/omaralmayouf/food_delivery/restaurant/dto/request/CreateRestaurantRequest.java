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

        @NotBlank(message = "Restaurant name is required")
        @Size(
                max = 50,
                message = "Restaurant name is too long, must be at most 50 characters"
        )
        String name,

        @Size(
                max = 160,
                message = "Restaurant description is too long, must be at most 160 characters"
        )
        String description,

        @URL(message = "Logo url must be valid")
        String logoUrl,

        @Valid
        @NotEmpty(message = "Working hours is required")
        List<WorkingHoursDto> workingHours,

        @Valid
        @NotNull(message = "Address is required")
        AddressDto address,

        @NotEmpty(message = "At least one cuisine is required")
        List<Long> cuisineIds) {
}
