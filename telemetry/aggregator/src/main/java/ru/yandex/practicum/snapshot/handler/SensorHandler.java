package ru.yandex.practicum.snapshot.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.Map;
import java.util.Optional;


public abstract class SensorHandler<T> {
    private static final Logger log = LoggerFactory.getLogger(SensorHandler.class);

    public abstract Class<T> getMessageType();

    Optional<Map<String, SensorStateAvro>> handle(SensorEventAvro eventAvro, SensorsSnapshotAvro snapshot) {
        Map<String, SensorStateAvro> states = snapshot.getSensorsState();
        SensorStateAvro deviceSnapshot;
        log.debug("Processing event for sensor: {}", eventAvro.getId());

        if (!states.isEmpty() && states.containsKey(eventAvro.getId())) {
            deviceSnapshot = states.get(eventAvro.getId());
            if (deviceSnapshot == null) {
                log.warn("No existing snapshot found for sensor: {}", eventAvro.getId());
                return Optional.empty();
            }
            if (deviceSnapshot.getTimestamp().isAfter(eventAvro.getTimestamp()) || deviceSnapshot.getData().equals(eventAvro.getPayload())) {
                return Optional.empty();
            }
        } else {
            deviceSnapshot = new SensorStateAvro();
        }
        deviceSnapshot.setTimestamp(eventAvro.getTimestamp());
        deviceSnapshot.setData(eventAvro.getPayload());

        states.put(eventAvro.getId(), deviceSnapshot);

        return Optional.of(states);
    }
}