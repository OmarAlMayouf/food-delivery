package io.github.omaralmayouf.food_delivery.restaurant.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "restaurants")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Restaurant {

    // Table ID - PrimaryKey
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    UUID id;

    // Metadata
    String name;
    String description;
    String logoUrl;
    BigDecimal rating;

    // Address
    @Embedded
    Address address;

    // Status
    boolean manuallyPaused;

    // Timestamps
    @CreationTimestamp
    Instant createdAt;

    @UpdateTimestamp
    Instant updatedAt;

}
