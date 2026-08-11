package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.CuisineDto;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Cuisine;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CuisineMapper {

    public CuisineDto toDtoFromEntity(Cuisine cuisine) {
        return CuisineDto.builder()
                .id(cuisine.getId())
                .name(cuisine.getName())
                .build();
    }

    public List<CuisineDto> toDtoListFromEntitySet(Set<Cuisine> cuisines) {
        return cuisines.stream().map(this::toDtoFromEntity).toList();
    }

}
