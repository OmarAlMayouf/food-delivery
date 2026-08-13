package io.github.omaralmayouf.food_delivery.shared.error;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ErrorResponseBody(

        String traceId, // unique id of the request

        int statusCode, // 400, 401, 403, 404, 500 ...

        ErrorCode errorCode, // BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, INTERNAL_SERVER_ERROR ...

        String description,  // translated message "Cuisine not found"

        Instant errorTimestamp, // timestamp of the error

        List<FieldValidationError> errorList
        // list of validation translated errors ["City is required", "Latitude is required"]

) {
}
