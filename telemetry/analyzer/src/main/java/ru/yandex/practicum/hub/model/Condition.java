package ru.yandex.practicum.hub.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "conditions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    @Column(nullable = false)
    private
    ConditionType type;
    @Column(nullable = false)
    private
    ConditionOperationType operation;
    @Column(nullable = false, name = "condition_value")
    private
    Integer value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    private
    Sensor conditionSource;

    public Condition(Long id, ConditionType type, ConditionOperationType operation, Integer value, Sensor conditionSource) {
        this.id = id;
        this.type = type;
        this.operation = operation;
        this.value = value;
        this.conditionSource = conditionSource;
    }

    public Condition() {
    }

    public Long getId() {
        return this.id;
    }

    public ConditionType getType() {
        return this.type;
    }

    public ConditionOperationType getOperation() {
        return this.operation;
    }

    public Integer getValue() {
        return this.value;
    }

    public Sensor getConditionSource() {
        return this.conditionSource;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public void setOperation(ConditionOperationType operation) {
        this.operation = operation;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setConditionSource(Sensor conditionSource) {
        this.conditionSource = conditionSource;
    }
}