package io.github.omaralmayouf.food_delivery.restaurant.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class Address {

    String city;

    String district;

    String street;

    BigDecimal latitude;

    BigDecimal longitude;
}
