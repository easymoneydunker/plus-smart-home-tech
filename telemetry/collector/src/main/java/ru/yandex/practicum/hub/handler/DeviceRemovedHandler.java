package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.hub.kafka.HubProducer;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DeviceRemovedHandler implements HubHandler {
    final HubProducer producer;

    public DeviceRemovedHandler(HubProducer producer) {
        this.producer = producer;
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_REMOVED;
    }

    @Override
    public void handle(HubEventProto eventProto) {
        DeviceRemovedEventProto deviceRemovedEventProto = eventProto.getDeviceRemoved();

        log.info("Handling DeviceRemoved event for device ID: {}", deviceRemovedEventProto.getId());
        log.debug("Received DeviceRemoved event: {}", eventProto);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(
                        DeviceRemovedEventAvro.newBuilder()
                                .setId(deviceRemovedEventProto.getId())
                                .build()
                )
                .build();

        log.debug("Constructed HubEventAvro: {}", eventAvro);

        producer.sendMessage(eventAvro);
        log.info("Successfully sent DeviceRemoved event for device ID: {}", deviceRemovedEventProto.getId());
    }
}
