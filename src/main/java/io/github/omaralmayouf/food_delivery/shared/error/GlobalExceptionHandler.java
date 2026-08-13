package io.github.omaralmayouf.food_delivery.shared.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseBody> handleBaseException(BaseException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        String description = translate(errorCode.getTranslationKey(), exception.getMessageParams());

        return buildResponse(errorCode, description, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseBody> handleValidationException(MethodArgumentNotValidException exception) {
        List<FieldValidationError> fieldErrors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        return buildResponse(errorCode, translate(errorCode.getTranslationKey()), fieldErrors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseBody> handleUnexpectedException(Exception exception) {

        log.error("Unhandled exception", exception);

        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;

        return buildResponse(errorCode, translate(errorCode.getTranslationKey()), List.of());
    }

    private String translate(String translationKey, Object... messageParams) {
        return messageSource.getMessage(
                translationKey,
                messageParams,
                LocaleContextHolder.getLocale()
        );
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(ErrorCode errorCode, String description, List<FieldValidationError> fieldErrors) {

        ErrorResponseBody body =
                new ErrorResponseBody(
                        UUID.randomUUID().toString(),
                        errorCode.getStatusCodeValue(),
                        errorCode,
                        description,
                        Instant.now(),
                        fieldErrors
                );

        return ResponseEntity.status(errorCode.getStatusCodeValue()).body(body);
    }

}
