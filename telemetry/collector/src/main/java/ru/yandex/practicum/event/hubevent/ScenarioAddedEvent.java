package ru.yandex.practicum.event.hubevent;

import ru.yandex.practicum.device.DeviceType;
import ru.yandex.practicum.device.action.DeviceAction;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.scenario.ScenarioCondition;

import java.util.List;

@Getter
@Setter
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
