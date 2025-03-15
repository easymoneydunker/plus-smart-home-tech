package ru.yandex.practicum.hub.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "conditions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    ConditionType type;
    @Column(nullable = false)
    ConditionOperationType operation;
    @Column(nullable = false, name = "condition_value")
    Integer value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    Sensor conditionSource;
}