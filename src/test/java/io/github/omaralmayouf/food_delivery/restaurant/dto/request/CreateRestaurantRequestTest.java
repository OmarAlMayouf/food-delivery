package io.github.omaralmayouf.food_delivery.restaurant.dto.request;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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

    private List<Tuple> violationsOf(CreateRestaurantRequest request) {
        return validator.validate(request).stream()
                .map(violation -> tuple(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
    }

    @Test
    void shouldAcceptAValidRequest() {
        assertThat(validator.validate(validRequest().build())).isEmpty();
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "   "})
    void shouldRejectMissingOrBlankName(String name) {
        assertThat(violationsOf(validRequest().name(name).build()))
                .containsExactly(tuple("name", "{error.restaurant.name.required}"));
    }

    @Test
    void shouldRejectNameLongerThanFiftyCharacters() {
        assertThat(violationsOf(validRequest().name("a".repeat(51)).build()))
                .containsExactly(tuple("name", "{error.restaurant.name.too_long}"));
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
        assertThat(violationsOf(validRequest().description("a".repeat(161)).build()))
                .containsExactly(tuple("description", "{error.restaurant.description.too_long}"));
    }

    @Test
    void shouldAcceptMissingLogoUrl() {
        assertThat(validator.validate(validRequest().logoUrl(null).build())).isEmpty();
    }

    @Test
    void shouldRejectMalformedLogoUrl() {
        assertThat(violationsOf(validRequest().logoUrl("not a url").build()))
                .containsExactly(tuple("logoUrl", "{error.restaurant.logo_url.not_valid}"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectMissingOrEmptyCuisineIds(List<Long> cuisineIds) {
        assertThat(violationsOf(validRequest().cuisineIds(cuisineIds).build()))
                .containsExactly(tuple("cuisineIds", "{error.restaurant.cuisines.required}"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectMissingOrEmptyWorkingHours(List<WorkingHoursDto> workingHours) {
        assertThat(violationsOf(validRequest().workingHours(workingHours).build()))
                .containsExactly(tuple("workingHours", "{error.restaurant.working_hours.required}"));
    }

    @Test
    void shouldRejectMissingAddress() {
        assertThat(violationsOf(validRequest().address(null).build()))
                .containsExactly(tuple("address", "{error.restaurant.address.required}"));
    }

    @Test
    void shouldRejectAnAddressWithABlankCity() {
        CreateRestaurantRequest request = validRequest()
                .address(validAddress().city("   ").build())
                .build();

        assertThat(violationsOf(request))
                .containsExactly(tuple("address.city", "{error.address.city.required}"));
    }

    @Test
    void shouldRejectWorkingHoursWithAnInvalidDayOfWeek() {
        CreateRestaurantRequest request = validRequest()
                .workingHours(List.of(validWorkingHours().dayOfWeek(7).build()))
                .build();

        assertThat(violationsOf(request))
                .containsExactly(tuple("workingHours[0].dayOfWeek", "{error.working_hours.day_of_week.max_value}"));
    }

}
