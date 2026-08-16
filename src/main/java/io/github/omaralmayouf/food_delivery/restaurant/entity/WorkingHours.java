package io.github.omaralmayouf.food_delivery.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import org.hibernate.annotations.UuidGenerator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@Table(name = "restaurant_working_hours")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class WorkingHours {

    // Table ID - PrimaryKey
    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    UUID id;

    // Relationship
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    int dayOfWeek;

    LocalTime openTime;

    LocalTime closeTime;

    static int toPostgresDayOfWeek(DayOfWeek day) {
        return day.getValue() % 7;
    }

    public boolean covers(LocalDateTime moment) {

        LocalTime time = moment.toLocalTime();

        if (crossesMidnight()) {

            if (matchesDay(moment.minusDays(1)) && time.isBefore(closeTime)) {
                return true;
            }

            return matchesDay(moment) && !time.isBefore(openTime);
        }

        return matchesDay(moment)
                && !time.isBefore(openTime)
                && time.isBefore(closeTime);
    }

    private boolean crossesMidnight() {
        return !closeTime.isAfter(openTime);
    }

    private boolean matchesDay(LocalDateTime moment) {
        return dayOfWeek == toPostgresDayOfWeek(moment.getDayOfWeek());
    }
}
