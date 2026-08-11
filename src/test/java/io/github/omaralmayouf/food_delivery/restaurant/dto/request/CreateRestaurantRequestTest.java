package io.github.omaralmayouf.food_delivery.restaurant.dto.request;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CreateRestaurantRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private static AddressDto.AddressDtoBuilder validAddress() {
        return AddressDto.builder()
                .city("city")
                .district("district")
                .street("street")
                .latitude(new BigDecimal("24.713600"))
                .longitude(new BigDecimal("46.675300"));
    }

    private static WorkingHoursDto.WorkingHoursDtoBuilder validWorkingHours() {
        return WorkingHoursDto.builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(17, 0));
    }

    private static CreateRestaurantRequest.CreateRestaurantRequestBuilder validRequest() {
        return CreateRestaurantRequest.builder()
                .name("restaurantA")
                .description("descriptionA")
                .logoUrl("https://example.com/logo.png")
                .address(validAddress().build())
                .workingHours(List.of(validWorkingHours().build()))
                .cuisineIds(List.of(1L, 2L));
    }

    private Set<String> rejectedFieldsOf(CreateRestaurantRequest request) {
        return validator.validate(request).stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
    }

    @Test
    void shouldAcceptAValidRequest() {
        assertThat(validator.validate(validRequest().build())).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankName(String name) {
        assertThat(rejectedFieldsOf(validRequest().name(name).build())).contains("name");
    }

    @Test
    void shouldRejectNameLongerThanFiftyCharacters() {
        assertThat(rejectedFieldsOf(validRequest().name("a".repeat(51)).build())).contains("name");
    }

    @Test
    void shouldAcceptNameOfExactlyFiftyCharacters() {
        assertThat(validator.validate(validRequest().name("a".repeat(50)).build())).isEmpty();
    }

    @Test
    void shouldAcceptMissingDescription() {
        assertThat(validator.validate(validRequest().description(null).build())).isEmpty();
    }

    @Test
    void shouldRejectDescriptionLongerThanOneSixty() {
        assertThat(rejectedFieldsOf(validRequest().description("a".repeat(161)).build()))
                .contains("description");
    }

    @Test
    void shouldAcceptMissingLogoUrl() {
        assertThat(validator.validate(validRequest().logoUrl(null).build())).isEmpty();
    }

    @Test
    void shouldRejectMalformedLogoUrl() {
        assertThat(rejectedFieldsOf(validRequest().logoUrl("not a url").build()))
                .contains("logoUrl");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectMissingOrEmptyCuisineIds(List<Long> cuisineIds) {
        assertThat(rejectedFieldsOf(validRequest().cuisineIds(cuisineIds).build()))
                .contains("cuisineIds");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectMissingOrEmptyWorkingHours(List<WorkingHoursDto> workingHours) {
        assertThat(rejectedFieldsOf(validRequest().workingHours(workingHours).build()))
                .contains("workingHours");
    }

    @Test
    void shouldRejectMissingAddress() {
        assertThat(rejectedFieldsOf(validRequest().address(null).build())).contains("address");
    }

    @Test
    void shouldRejectAnAddressWithABlankCity() {
        CreateRestaurantRequest request = validRequest()
                .address(validAddress().city("   ").build())
                .build();

        assertThat(rejectedFieldsOf(request)).contains("address.city");
    }

    @Test
    void shouldRejectWorkingHoursWithAnInvalidDayOfWeek() {
        CreateRestaurantRequest request = validRequest()
                .workingHours(List.of(validWorkingHours().dayOfWeek(7).build()))
                .build();

        assertThat(rejectedFieldsOf(request)).contains("workingHours[0].dayOfWeek");
    }

}
