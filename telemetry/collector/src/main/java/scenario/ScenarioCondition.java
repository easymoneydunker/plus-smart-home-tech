package scenario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScenarioCondition {
    private int sensorId;
    private ConditionComparisonType comparisonType;
    private int value;
}
