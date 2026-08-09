package io.github.omaralmayouf.food_delivery.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import org.hibernate.annotations.UuidGenerator;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "restaurant_working_hours")
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class WorkingHours {

    // Table ID - PrimaryKey
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @EqualsAndHashCode.Include
    UUID id;

    // Relationship
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    int dayOfWeek;

    LocalTime openTime;

    LocalTime closeTime;
}
