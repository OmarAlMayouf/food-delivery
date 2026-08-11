package io.github.omaralmayouf.food_delivery.restaurant.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    // Relationship
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "cuisine_restaurant",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id")
    )
    @Builder.Default
    Set<Cuisine> cuisines = new HashSet<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    Set<WorkingHours> workingHours = new HashSet<>();

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

    public boolean isAcceptingOrders() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Riyadh"));
        boolean isRestaurantOpen = workingHours.stream().anyMatch(hours -> hours.covers(now));
        return !manuallyPaused && isRestaurantOpen;
    }

    public void addWorkingHours(WorkingHours hours) {
        hours.setRestaurant(this);
        this.workingHours.add(hours);
    }

}
