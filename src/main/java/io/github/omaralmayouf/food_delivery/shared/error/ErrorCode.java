package io.github.omaralmayouf.food_delivery.shared.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;

@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public enum ErrorCode {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "error.validation"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "error.internal"),

    CUISINE_NOT_FOUND(HttpStatus.UNPROCESSABLE_CONTENT, "error.cuisine.not_found"),
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "error.restaurant.not_found");

    final HttpStatus statusCode;

    @Getter
    final String translationKey;

    public int getStatusCodeValue() {
        return statusCode.value();
    }

}
