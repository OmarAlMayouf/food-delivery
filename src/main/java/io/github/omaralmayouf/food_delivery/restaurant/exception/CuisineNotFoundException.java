package io.github.omaralmayouf.food_delivery.restaurant.exception;

import io.github.omaralmayouf.food_delivery.shared.error.BaseException;
import io.github.omaralmayouf.food_delivery.shared.error.ErrorCode;

import java.util.List;

public class CuisineNotFoundException extends BaseException {

    public CuisineNotFoundException(List<Long> missingIds) {
        super(ErrorCode.CUISINE_NOT_FOUND, missingIds);
    }

}
