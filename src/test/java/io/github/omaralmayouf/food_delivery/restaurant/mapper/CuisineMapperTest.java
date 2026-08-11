package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.CuisineDto;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Cuisine;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CuisineMapperTest {

    private final CuisineMapper cuisineMapper = new CuisineMapper();

    @Test
    void shouldReturnCuisineDtoFromEntity() {
        Cuisine cuisine = Cuisine.builder()
                .id(1L)
                .name("cuisine123")
                .build();

        CuisineDto dto = cuisineMapper.toDtoFromEntity(cuisine);

        assertThat(dto.id()).isEqualTo(1L);
        assertThat(dto.name()).isEqualTo("cuisine123");
    }

    @Test
    void shouldReturnCuisineDtoListFromEntitySet() {
        Cuisine cuisine1 = Cuisine.builder()
                .id(1L)
                .name("cuisineB")
                .build();
        Cuisine cuisine2 = Cuisine.builder()
                .id(2L)
                .name("cuisineA")
                .build();

        Set<Cuisine> cuisines = Set.of(cuisine1, cuisine2);

        List<CuisineDto> dtoList = cuisineMapper.toDtoListFromEntitySet(cuisines);

        assertThat(dtoList)
                .hasSize(2)
                .extracting(CuisineDto::id, CuisineDto::name)
                .containsExactly(
                        Tuple.tuple(2L, "cuisineA"),
                        Tuple.tuple(1L, "cuisineB")
                );
    }
}
