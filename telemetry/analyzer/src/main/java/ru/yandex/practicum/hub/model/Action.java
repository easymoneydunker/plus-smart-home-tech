package ru.yandex.practicum.hub.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "actions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    ActionType type;
    @Column(nullable = false, name = "action_value")
    Integer value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    Sensor actionPerformer;
}