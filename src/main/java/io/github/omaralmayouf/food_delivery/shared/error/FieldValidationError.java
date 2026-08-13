package io.github.omaralmayouf.food_delivery.shared.error;

public record FieldValidationError(

        String fieldName,

        String message

) {
}
