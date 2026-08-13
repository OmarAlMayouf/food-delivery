package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;
import io.github.omaralmayouf.food_delivery.restaurant.entity.WorkingHours;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class WorkingHoursMapper {

    public WorkingHoursDto toDtoFromEntity(WorkingHours workingHours) {
        return WorkingHoursDto.builder()
                .dayOfWeek(workingHours.getDayOfWeek())
                .openTime(workingHours.getOpenTime())
                .closeTime(workingHours.getCloseTime())
                .build();
    }

    public WorkingHours toEntityFromDto(WorkingHoursDto workingHoursDto) {
        return WorkingHours.builder()
                .dayOfWeek(workingHoursDto.dayOfWeek())
                .openTime(workingHoursDto.openTime())
                .closeTime(workingHoursDto.closeTime())
                .build();
    }

    public List<WorkingHoursDto> toDtoListFromEntitySet(Set<WorkingHours> workingHours) {
        return workingHours.stream().map(this::toDtoFromEntity).toList();
    }

    public List<WorkingHours> toEntityListFromDtoList(List<WorkingHoursDto> workingHoursDtoList) {
        return workingHoursDtoList.stream().map(this::toEntityFromDto).toList();
    }

}
