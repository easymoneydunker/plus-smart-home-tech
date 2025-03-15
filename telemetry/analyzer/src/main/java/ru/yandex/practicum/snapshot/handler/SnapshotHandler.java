package ru.yandex.practicum.snapshot.handler;

import com.google.protobuf.Timestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.hub.model.Action;
import ru.yandex.practicum.hub.model.Condition;
import ru.yandex.practicum.hub.model.Scenario;
import ru.yandex.practicum.hub.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;
import ru.yandex.practicum.snapshot.service.SnapshotService;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Component
@Slf4j
public class SnapshotHandler {
    final ScenarioRepository scenarioRepository;
    final SnapshotService service;

    public void handle(SensorsSnapshotAvro sensorsSnapshotAvro) {
        log.info("Обработка снапшота хаба {}", sensorsSnapshotAvro.getHubId());

        List<Scenario> scenarios = scenarioRepository.findByHubId(sensorsSnapshotAvro.getHubId());
        Map<String, SensorStateAvro> states = sensorsSnapshotAvro.getSensorsState();

        for (Scenario scenario: scenarios) {
            boolean flag = true;

            for (Condition condition: scenario.getConditions()) {
                List<Integer> values = condition.getType().cast(
                        states.get(condition.getConditionSource().getId())
                );

                boolean innerFlag = false;

                for (Integer value : values) {
                    innerFlag = innerFlag || condition.getOperation().handle(condition.getValue(), value);
                }

                flag = flag && innerFlag;
            }

            if (flag) {
                for (Action action: scenario.getActions()) {
                    service.sendMessage(getRequest(sensorsSnapshotAvro, scenario, action));
                }
            }
        }

        log.info("Завершена обработка снапшота хаба " + sensorsSnapshotAvro.getHubId());
    }

    private static DeviceActionRequest getRequest(SensorsSnapshotAvro sensorsSnapshotAvro, Scenario scenario, Action action) {
        return DeviceActionRequest.newBuilder()
                .setHubId(sensorsSnapshotAvro.getHubId())
                .setScenarioName(scenario.getName())
                .setAction(
                        DeviceActionProto.newBuilder()
                                .setSensorId(action.getActionPerformer().getId())
                                .setType(ActionTypeProto.valueOf(action.getType().name()))
                                .setValue(action.getValue())
                                .build()
                )
                .setTimestamp(
                        Timestamp.newBuilder()
                                .setSeconds(Instant.now().getEpochSecond())
                                .setNanos(Instant.now().getNano())
                                .build()
                )
                .build();
    }
}