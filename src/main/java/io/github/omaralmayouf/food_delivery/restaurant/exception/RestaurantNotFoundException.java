package io.github.omaralmayouf.food_delivery.restaurant.exception;

import io.github.omaralmayouf.food_delivery.shared.error.BaseException;
import io.github.omaralmayouf.food_delivery.shared.error.ErrorCode;

import java.util.UUID;

public class RestaurantNotFoundException extends BaseException {

    public RestaurantNotFoundException(UUID restaurantId) {
        super(ErrorCode.RESTAURANT_NOT_FOUND, restaurantId);
    }

}
