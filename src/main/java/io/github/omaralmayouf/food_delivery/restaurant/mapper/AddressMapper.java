package io.github.omaralmayouf.food_delivery.restaurant.mapper;

import io.github.omaralmayouf.food_delivery.restaurant.dto.AddressDto;
import io.github.omaralmayouf.food_delivery.restaurant.entity.Address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto toDtoFromEntity(Address address) {
        return AddressDto.builder()
                .city(address.getCity())
                .district(address.getDistrict())
                .street(address.getStreet())
                .latitude(address.getLatitude())
                .longitude(address.getLongitude())
                .build();
    }

    public Address toEntityFromDto(AddressDto addressDto) {
        return Address.builder()
                .city(addressDto.city())
                .district(addressDto.district())
                .street(addressDto.street())
                .latitude(addressDto.latitude())
                .longitude(addressDto.longitude())
                .build();
    }

}
