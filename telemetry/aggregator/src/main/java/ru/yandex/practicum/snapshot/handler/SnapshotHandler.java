package ru.yandex.practicum.snapshot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SnapshotHandler {
    final Map<String, SensorsSnapshotAvro> snapshots;
    final Map<Class<? extends SpecificRecord>, SensorHandler<? extends SpecificRecord>> handlers;

    public SnapshotHandler(List<SensorHandler<? extends SpecificRecord>> handlers) {
        snapshots = new HashMap<>();
        this.handlers = new HashMap<>();
        for (SensorHandler<? extends SpecificRecord> handler : handlers) {
            this.handlers.put(handler.getMessageType(), handler);
        }
        log.info("Initialized SnapshotHandler with {} sensor handlers.", handlers.size());
    }

    public Optional<SensorsSnapshotAvro> handleKafkaMessage(SensorEventAvro eventAvro) {
        String hubId = eventAvro.getHubId();
        log.debug("Processing event for hub: {}", hubId);

        SensorsSnapshotAvro snapshot = snapshots.getOrDefault(hubId,
                SensorsSnapshotAvro.newBuilder()
                        .setHubId(hubId)
                        .setTimestamp(Instant.now())
                        .setSensorsState(new HashMap<>())
                        .build());
        log.debug("Processed snapshot with timestamp: {}", snapshot.getTimestamp());

        Class<?> payloadClass = eventAvro.getPayload().getClass();
        if (!handlers.containsKey(payloadClass)) {
            log.warn("No handler registered for event type: {}", payloadClass.getSimpleName());
            return Optional.empty();
        }

        log.debug("Found handler for event type: {}", payloadClass.getSimpleName());
        Optional<SensorsSnapshotAvro> result = handlers.get(payloadClass).handle(eventAvro, snapshot);

        result.ifPresent(updatedSnapshot -> {
            snapshots.put(hubId, updatedSnapshot);
            log.info("Updated snapshot for hub: {}", hubId);
        });

        return result;
    }
}
