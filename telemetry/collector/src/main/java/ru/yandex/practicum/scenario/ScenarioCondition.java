package ru.yandex.practicum.scenario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScenarioCondition {
    private int sensorId;
    private ConditionComparisonType comparisonType;
    private ScenarioType scenarioType;
    private int value;
}
