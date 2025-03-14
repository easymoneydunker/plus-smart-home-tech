package ru.yandex.practicum.snapshot.handler;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Slf4j
public abstract class SensorHandler<T> {
    public abstract Class<T> getMessageType();

    Optional<SensorsSnapshotAvro> handle(SensorEventAvro eventAvro, SensorsSnapshotAvro snapshot) {
        log.debug("Processing event for sensor: {}", eventAvro.getId());

        T event = (T) eventAvro.getPayload();
        Map<String, SensorStateAvro> states = snapshot.getSensorsState();
        SensorStateAvro deviceSnapshot = states.get(eventAvro.getId());

        if (deviceSnapshot == null) {
            log.warn("No existing snapshot found for sensor: {}", eventAvro.getId());
            return Optional.empty();
        }

        T oldEvent = (T) deviceSnapshot.getData();

        if (eventAvro.getTimestamp().isBefore(deviceSnapshot.getTimestamp())) {
            log.debug("Discarding outdated event for sensor: {}", eventAvro.getId());
            return Optional.empty();
        }

        if (event.equals(oldEvent)) {
            log.debug("Ignoring unchanged event for sensor: {}", eventAvro.getId());
            return Optional.empty();
        }

        log.info("Updating snapshot for sensor: {}", eventAvro.getId());
        deviceSnapshot.setData(event);
        states.put(eventAvro.getId(), deviceSnapshot);
        snapshot.setSensorsState(states);
        snapshot.setTimestamp(Instant.now());

        return Optional.of(snapshot);
    }
}
