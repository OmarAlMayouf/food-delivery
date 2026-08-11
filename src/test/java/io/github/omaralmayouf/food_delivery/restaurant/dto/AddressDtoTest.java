package io.github.omaralmayouf.food_delivery.restaurant.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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

    static Stream<Arguments> valuesOverTheLengthLimit() {
        return Stream.of(
                Arguments.of(validAddress().city("a".repeat(51)).build(), "city"),
                Arguments.of(validAddress().district("a".repeat(51)).build(), "district"),
                Arguments.of(validAddress().street("a".repeat(101)).build(), "street")
        );
    }

    static Stream<AddressDto> valuesAtTheLengthLimit() {
        return Stream.of(
                validAddress().city("a".repeat(50)).build(),
                validAddress().district("a".repeat(50)).build(),
                validAddress().street("a".repeat(100)).build()
        );
    }

    private Set<String> rejectedFieldsOf(AddressDto address) {
        return validator.validate(address).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }

    @Test
    void shouldAcceptAValidAddress() {
        assertThat(validator.validate(validAddress().build())).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankCity(String city) {
        assertThat(rejectedFieldsOf(validAddress().city(city).build())).contains("city");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankDistrict(String district) {
        assertThat(rejectedFieldsOf(validAddress().district(district).build())).contains("district");
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankStreet(String street) {
        assertThat(rejectedFieldsOf(validAddress().street(street).build())).contains("street");
    }

    @ParameterizedTest
    @MethodSource("valuesOverTheLengthLimit")
    void shouldRejectTextLongerThanTheLimit(AddressDto address, String field) {
        assertThat(rejectedFieldsOf(address)).contains(field);
    }

    @ParameterizedTest
    @MethodSource("valuesAtTheLengthLimit")
    void shouldAcceptTextExactlyAtTheLimit(AddressDto address) {
        assertThat(validator.validate(address)).isEmpty();
    }

    @Test
    void shouldRejectMissingLatitude() {
        assertThat(rejectedFieldsOf(validAddress().latitude(null).build())).contains("latitude");
    }

    @Test
    void shouldRejectMissingLongitude() {
        assertThat(rejectedFieldsOf(validAddress().longitude(null).build())).contains("longitude");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-90.1", "90.1", "1000"})
    void shouldRejectLatitudeOutsideItsRange(String latitude) {
        assertThat(rejectedFieldsOf(validAddress().latitude(new BigDecimal(latitude)).build()))
                .contains("latitude");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-90", "0", "90"})
    void shouldAcceptLatitudeInsideItsRange(String latitude) {
        assertThat(validator.validate(validAddress().latitude(new BigDecimal(latitude)).build()))
                .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"-180.1", "180.1", "1000"})
    void shouldRejectLongitudeOutsideItsRange(String longitude) {
        assertThat(rejectedFieldsOf(validAddress().longitude(new BigDecimal(longitude)).build()))
                .contains("longitude");
    }

    @ParameterizedTest
    @ValueSource(strings = {"-180", "0", "180"})
    void shouldAcceptLongitudeInsideItsRange(String longitude) {
        assertThat(validator.validate(validAddress().longitude(new BigDecimal(longitude)).build()))
                .isEmpty();
    }

}
