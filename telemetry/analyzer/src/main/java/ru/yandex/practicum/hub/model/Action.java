package ru.yandex.practicum.hub.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "actions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private
    Long id;
    @Column(nullable = false)
    private
    ActionType type;
    @Column(nullable = false, name = "action_value")
    private
    Integer value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    private
    Sensor actionPerformer;

    public Action(Long id, ActionType type, Integer value, Sensor actionPerformer) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.actionPerformer = actionPerformer;
    }

    public Action() {
    }

    public Long getId() {
        return this.id;
    }

    public ActionType getType() {
        return this.type;
    }

    public Integer getValue() {
        return this.value;
    }

    public Sensor getActionPerformer() {
        return this.actionPerformer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setActionPerformer(Sensor actionPerformer) {
        this.actionPerformer = actionPerformer;
    }
}