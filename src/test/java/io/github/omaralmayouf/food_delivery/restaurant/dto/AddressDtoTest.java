package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AddressDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static AddressDto.AddressDtoBuilder validAddress() {
        return AddressDto.builder()
                .city("city")
                .district("district")
                .street("street")
                .latitude(new BigDecimal("24.713600"))
                .longitude(new BigDecimal("46.675300"));
    }

    private List<Tuple> violationsOf(AddressDto address) {
        return validator.validate(address).stream()
                .map(violation -> tuple(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
    }

    static Stream<Arguments> valuesOverTheLengthLimit() {
        return Stream.of(
                Arguments.of(validAddress().city("a".repeat(51)).build(), "city", "{error.address.city.too_long}"),
                Arguments.of(validAddress().district("a".repeat(51)).build(), "district", "{error.address.district.too_long}"),
                Arguments.of(validAddress().street("a".repeat(101)).build(), "street", "{error.address.street.too_long}")
        );
    }

    static Stream<AddressDto> valuesAtTheLengthLimit() {
        return Stream.of(
                validAddress().city("a".repeat(50)).build(),
                validAddress().district("a".repeat(50)).build(),
                validAddress().street("a".repeat(100)).build()
        );
    }

    @Test
    void shouldAcceptAValidAddress() {
        assertThat(validator.validate(validAddress().build())).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankCity(String city) {
        assertThat(violationsOf(validAddress().city(city).build()))
                .containsExactly(tuple("city", "{error.address.city.required}"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankDistrict(String district) {
        assertThat(violationsOf(validAddress().district(district).build()))
                .containsExactly(tuple("district", "{error.address.district.required}"));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankStreet(String street) {
        assertThat(violationsOf(validAddress().street(street).build()))
                .containsExactly(tuple("street", "{error.address.street.required}"));
    }

    @ParameterizedTest
    @MethodSource("valuesOverTheLengthLimit")
    void shouldRejectTextLongerThanTheLimit(AddressDto address, String field, String translationKey) {
        assertThat(violationsOf(address)).containsExactly(tuple(field, translationKey));
    }

    @ParameterizedTest
    @MethodSource("valuesAtTheLengthLimit")
    void shouldAcceptTextExactlyAtTheLimit(AddressDto address) {
        assertThat(validator.validate(address)).isEmpty();
    }

    @Test
    void shouldRejectMissingLatitude() {
        assertThat(violationsOf(validAddress().latitude(null).build()))
                .containsExactly(tuple("latitude", "{error.address.latitude.required}"));
    }

    @Test
    void shouldRejectMissingLongitude() {
        assertThat(violationsOf(validAddress().longitude(null).build()))
                .containsExactly(tuple("longitude", "{error.address.longitude.required}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-90.1", "-1000"})
    void shouldRejectLatitudeBelowItsRange(String latitude) {
        assertThat(violationsOf(validAddress().latitude(new BigDecimal(latitude)).build()))
                .containsExactly(tuple("latitude", "{error.address.latitude.min_value}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"90.1", "1000"})
    void shouldRejectLatitudeAboveItsRange(String latitude) {
        assertThat(violationsOf(validAddress().latitude(new BigDecimal(latitude)).build()))
                .containsExactly(tuple("latitude", "{error.address.latitude.max_value}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-90", "0", "90"})
    void shouldAcceptLatitudeInsideItsRange(String latitude) {
        assertThat(validator.validate(validAddress().latitude(new BigDecimal(latitude)).build()))
                .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-180.1", "-1000"})
    void shouldRejectLongitudeBelowItsRange(String longitude) {
        assertThat(violationsOf(validAddress().longitude(new BigDecimal(longitude)).build()))
                .containsExactly(tuple("longitude", "{error.address.longitude.min_value}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"180.1", "1000"})
    void shouldRejectLongitudeAboveItsRange(String longitude) {
        assertThat(violationsOf(validAddress().longitude(new BigDecimal(longitude)).build()))
                .containsExactly(tuple("longitude", "{error.address.longitude.max_value}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-180", "0", "180"})
    void shouldAcceptLongitudeInsideItsRange(String longitude) {
        assertThat(validator.validate(validAddress().longitude(new BigDecimal(longitude)).build()))
                .isEmpty();
    }

}
