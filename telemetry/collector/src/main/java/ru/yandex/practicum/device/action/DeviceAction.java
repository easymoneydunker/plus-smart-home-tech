package ru.yandex.practicum.device.action;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceAction {
    private int deviceId;
    private ActionType actionType;
    private int value;
}
