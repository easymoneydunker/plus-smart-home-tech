package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.hub.kafka.HubProducer;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DeviceAddedHandler implements HubHandler {
    final HubProducer producer;

    @Autowired
    public DeviceAddedHandler(HubProducer producer) {
        this.producer = producer;
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.DEVICE_ADDED;
    }

    @Override
    public void handle(HubEventProto eventProto) {
        DeviceAddedEventProto deviceAddedEventProto = eventProto.getDeviceAdded();

        log.info("Handling DeviceAdded event for device ID: {}", deviceAddedEventProto.getId());
        log.debug("Received DeviceAdded event: {}", eventProto);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(
                        DeviceAddedEventAvro.newBuilder()
                                .setId(deviceAddedEventProto.getId())
                                .setType(DeviceTypeAvro.valueOf(deviceAddedEventProto.getType().name()))
                                .build()
                )
                .build();

        log.debug("Constructed HubEventAvro: {}", eventAvro);

        producer.sendMessage(eventAvro);
        log.info("Successfully sent DeviceAdded event for device ID: {}", deviceAddedEventProto.getId());
    }
}
