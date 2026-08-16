package io.github.omaralmayouf.food_delivery.restaurant.controller;

import io.github.omaralmayouf.food_delivery.restaurant.dto.response.RestaurantResponse;
import io.github.omaralmayouf.food_delivery.restaurant.exception.CuisineNotFoundException;
import io.github.omaralmayouf.food_delivery.restaurant.exception.RestaurantNotFoundException;
import io.github.omaralmayouf.food_delivery.restaurant.service.RestaurantService;
import io.github.omaralmayouf.food_delivery.shared.config.SecurityConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@Import(SecurityConfiguration.class)
@ImportAutoConfiguration(MessageSourceAutoConfiguration.class)
class RestaurantControllerTest {

    private static final String VALID_BODY = """
            {
              "name": "restaurantA",
              "description": "descriptionA",
              "workingHours": [
                {"dayOfWeek": 0, "openTime": "09:00", "closeTime": "23:00"}
              ],
              "address": {
                "city": "Riyadh",
                "district": "Olaya",
                "street": "King Fahd Road",
                "latitude": 24.7136,
                "longitude": 46.6753
              },
              "cuisineIds": [1]
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    @Test
    void shouldReturn201AndTheCreatedRestaurant() throws Exception {
        RestaurantResponse response = RestaurantResponse.builder()
                .id(UUID.randomUUID())
                .name("restaurantA")
                .build();
        when(restaurantService.createRestaurant(any())).thenReturn(response);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.name").value("restaurantA"));
    }

    @Test
    void shouldReturn400WithFieldErrorsForInvalidBody() throws Exception {
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.errorList[*].fieldName").exists());
    }

    @Test
    void shouldTranslateValidationErrorsToArabic() throws Exception {
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .header("Accept-Language", "ar"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("فشل التحقق"));
    }

    @Test
    void shouldReturn422WhenACuisineDoesNotExist() throws Exception {
        when(restaurantService.createRestaurant(any()))
                .thenThrow(new CuisineNotFoundException(List.of(99L)));

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.errorCode").value("CUISINE_NOT_FOUND"));
    }

    @Test
    void shouldReturn404ForUnknownRestaurantId() throws Exception {
        UUID unknownId = UUID.randomUUID();
        when(restaurantService.getRestaurantById(unknownId))
                .thenThrow(new RestaurantNotFoundException(unknownId));

        mockMvc.perform(get("/restaurants/{id}", unknownId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("RESTAURANT_NOT_FOUND"));
    }

    @Test
    void shouldReturn400ForMalformedJsonBody() throws Exception {
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"a\", \"workingHours\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400ForNonUuidPathVariable() throws Exception {
        mockMvc.perform(get("/restaurants/{id}", "not-a-uuid"))
                .andExpect(status().isBadRequest());
    }

}
