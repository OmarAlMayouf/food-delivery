package io.github.omaralmayouf.food_delivery.shared.error;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public abstract class BaseException extends RuntimeException {

    final ErrorCode errorCode;
    final transient Object[] messageParams;

    public BaseException(ErrorCode errorCode, Object... messageParams) {
        super(errorCode.getTranslationKey());
        this.errorCode = errorCode;
        this.messageParams = messageParams;
    }

}
