package event.hubevent;

import device.DeviceType;

public class DeviceAddedEvent extends HubEvent {
    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED_EVENT;
    }
}
