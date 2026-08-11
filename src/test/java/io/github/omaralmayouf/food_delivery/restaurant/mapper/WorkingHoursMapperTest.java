package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.WorkingHoursDto;
import io.github.omaralmayouf.food_delivery.restaurant.entity.WorkingHours;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class WorkingHoursMapperTest {

    private final WorkingHoursMapper workingHoursMapper = new WorkingHoursMapper();

    @Test
    void shouldReturnWorkingHoursDtoFromEntity() {
        WorkingHours workingHours = WorkingHours
                .builder()
                .id(UUID.randomUUID())
                .restaurant(null)
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        WorkingHoursDto workingHoursDto = workingHoursMapper.toDtoFromEntity(workingHours);

        assertThat(workingHoursDto).isNotNull();
        assertThat(workingHoursDto.dayOfWeek()).isEqualTo(0);
        assertThat(workingHoursDto.openTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(workingHoursDto.closeTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void shouldReturnWorkingHoursEntityFromDto() {
        WorkingHoursDto workingHoursDto = WorkingHoursDto
                .builder()
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        WorkingHours workingHours = workingHoursMapper.toEntityFromDto(workingHoursDto);

        assertThat(workingHours).isNotNull();
        assertThat(workingHours.getDayOfWeek()).isEqualTo(0);
        assertThat(workingHours.getOpenTime()).isEqualTo(LocalTime.of(10, 0));
        assertThat(workingHours.getCloseTime()).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void shouldReturnWorkingHoursDtoListFromEntitySet() {
        WorkingHours workingHours1 = WorkingHours
                .builder()
                .id(UUID.randomUUID())
                .restaurant(null)
                .dayOfWeek(0)
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(18, 0))
                .build();

        WorkingHours workingHours2 = WorkingHours
                .builder()
                .id(UUID.randomUUID())
                .restaurant(null)
                .dayOfWeek(1)
                .openTime(LocalTime.of(7, 0))
                .closeTime(LocalTime.of(20, 0))
                .build();

        Set<WorkingHours> workingHoursSet = Set.of(workingHours1, workingHours2);

        List<WorkingHoursDto> workingHoursDtoList = workingHoursMapper.toDtoListFromEntitySet(workingHoursSet);

        assertThat(workingHoursDtoList)
                .hasSize(2)
                .extracting(WorkingHoursDto::dayOfWeek, WorkingHoursDto::openTime, WorkingHoursDto::closeTime)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(0, LocalTime.of(10, 0), LocalTime.of(18, 0)),
                        Tuple.tuple(1, LocalTime.of(7, 0), LocalTime.of(20, 0))
                );
    }

}
