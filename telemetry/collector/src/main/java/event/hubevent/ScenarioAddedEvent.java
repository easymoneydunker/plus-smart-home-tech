package event.hubevent;

import device.DeviceType;
import device.action.DeviceAction;
import scenario.ScenarioCondition;

import java.util.List;

public class ScenarioAddedEvent extends HubEvent {
    DeviceType deviceType;
    private String name;
    private List<ScenarioCondition> conditions;
    private List<DeviceAction> actions;

    @Override
    public HubEventType getType() {
        return HubEventType.SCENARIO_ADDED_EVENT;
    }
}
