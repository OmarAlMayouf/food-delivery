package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Address;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapper();

    @Test
    void shouldReturnAddressDtoFromAddress() {
        Address address = Address.builder()
                .city("city123")
                .district("district123")
                .street("street123")
                .latitude(new BigDecimal("24.713600"))
                .longitude(new BigDecimal("46.675300"))
                .build();

        AddressDto dto = addressMapper.toDtoFromEntity(address);

        assertThat(dto.city()).isEqualTo("city123");
        assertThat(dto.district()).isEqualTo("district123");
        assertThat(dto.street()).isEqualTo("street123");
        assertThat(dto.latitude()).isEqualByComparingTo("24.713600");
        assertThat(dto.longitude()).isEqualByComparingTo("46.675300");
    }

    @Test
    void shouldReturnAddressFromAddressDto() {
        AddressDto dto = AddressDto.builder()
                .city("city123")
                .district("district123")
                .street("street123")
                .latitude(new BigDecimal("24.713600"))
                .longitude(new BigDecimal("46.675300"))
                .build();

        Address address = addressMapper.toEntityFromDto(dto);

        assertThat(address.getCity()).isEqualTo("city123");
        assertThat(address.getDistrict()).isEqualTo("district123");
        assertThat(address.getStreet()).isEqualTo("street123");
        assertThat(address.getLatitude()).isEqualByComparingTo("24.713600");
        assertThat(address.getLongitude()).isEqualByComparingTo("46.675300");
    }
}
