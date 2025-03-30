package ru.yandex.practicum.snapshot.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.AggregatorStarter;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SnapshotHandler {
    final Map<String, SensorsSnapshotAvro> snapshots;
    final Map<Class<? extends SpecificRecord>, SensorHandler<? extends SpecificRecord>> handlers;
    private static final Logger log = LoggerFactory.getLogger(SnapshotHandler.class);

    public SnapshotHandler(List<SensorHandler<? extends SpecificRecord>> handlers) {
        snapshots = new HashMap<>();
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(
                        SensorHandler::getMessageType,
                        Function.identity()
                ));
    }

    public Optional<SensorsSnapshotAvro> handleKafkaMessage(SensorEventAvro eventAvro) {
        String hubId = eventAvro.getHubId();
        log.info("Начата агрегация данных хаба {}", hubId);

        SensorsSnapshotAvro snapshot;
        if (snapshots.containsKey(hubId)) {
            snapshot = snapshots.get(hubId);
        } else {
            snapshot = SensorsSnapshotAvro.newBuilder()
                    .setHubId(hubId)
                    .setTimestamp(Instant.now())
                    .setSensorsState(new HashMap<>())
                    .build();

            snapshots.put(hubId, snapshot);
        }

        if (!handlers.containsKey(eventAvro.getPayload().getClass())) {
            return Optional.empty();
        } else {
            Map<String, SensorStateAvro> result = handlers.get(eventAvro.getPayload().getClass())
                    .handle(eventAvro, snapshot).orElse(null);

            if (result == null) {
                return Optional.empty();
            }

            snapshot.setSensorsState(result);
            return Optional.of(snapshot);
        }
    }
}