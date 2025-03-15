package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.hub.kafka.HubProducer;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ScenarioAddedHandler implements HubHandler {
    final HubProducer producer;

    public ScenarioAddedHandler(HubProducer producer) {
        this.producer = producer;
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEventProto eventProto) {
        ScenarioAddedEventProto scenarioAddedEventProto = eventProto.getScenarioAdded();

        log.info("Handling ScenarioAdded event with name: {}", scenarioAddedEventProto.getName());
        log.debug("Received ScenarioAdded event: {}", eventProto);

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(
                        ScenarioAddedEventAvro.newBuilder()
                                .setName(scenarioAddedEventProto.getName())
                                .setConditions(
                                        scenarioAddedEventProto.getConditionList().stream()
                                                .map(this::mapScenarioCondition)
                                                .collect(Collectors.toList())
                                )
                                .setActions(
                                        scenarioAddedEventProto.getActionList().stream()
                                                .map(this::mapDeviceAction)
                                                .collect(Collectors.toList())
                                )
                                .build()
                )
                .build();

        log.debug("Constructed HubEventAvro: {}", eventAvro);

        producer.sendMessage(eventAvro);
        log.info("Successfully sent ScenarioAdded event for name: {}", scenarioAddedEventProto.getName());
    }

    private ScenarioConditionAvro mapScenarioCondition(ScenarioConditionProto scenarioConditionProto) {
        log.debug("Mapping ScenarioConditionProto: {}", scenarioConditionProto);

        ScenarioConditionAvro.Builder builder = ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioConditionProto.getSensorId())
                .setOperation(ConditionOperationAvro.valueOf(scenarioConditionProto.getOperation().name()))
                .setType(ConditionTypeAvro.valueOf(scenarioConditionProto.getType().name()));

        if (scenarioConditionProto.hasIntValue()) {
            builder.setValue(scenarioConditionProto.getIntValue());
            log.debug("Setting int value: {}", scenarioConditionProto.getIntValue());
        } else if (scenarioConditionProto.hasBoolValue()) {
            builder.setValue(scenarioConditionProto.getBoolValue());
            log.debug("Setting bool value: {}", scenarioConditionProto.getBoolValue());
        }

        return builder.build();
    }


    private DeviceActionAvro mapDeviceAction(DeviceActionProto deviceActionProto) {
        log.debug("Mapping DeviceActionProto: {}", deviceActionProto);

        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceActionProto.getSensorId())
                .setValue(deviceActionProto.getValue())
                .setType(ActionTypeAvro.valueOf(deviceActionProto.getType().name()))
                .build();
    }
}
