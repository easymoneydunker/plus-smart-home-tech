package event.hubevent;

import device.DeviceType;

public class DeviceRemovedEvent extends HubEvent {
    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED_EVENT;
    }
}
