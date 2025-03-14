package ru.yandex.practicum.hub.handler;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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

        producer.sendMessage(eventAvro);
    }

    private ScenarioConditionAvro mapScenarioCondition(ScenarioConditionProto scenarioConditionProto) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioConditionProto.getSensorId())
                .setValue(scenarioConditionProto.getBoolValue())
                .setOperation(ConditionOperationAvro.valueOf(scenarioConditionProto.getOperation().name()))
                .setType(ConditionTypeAvro.valueOf(scenarioConditionProto.getType().name()))
                .build();
    }

    private DeviceActionAvro mapDeviceAction(DeviceActionProto deviceActionProto) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceActionProto.getSensorId())
                .setValue(deviceActionProto.getValue())
                .setType(ActionTypeAvro.valueOf(deviceActionProto.getType().name()))
                .build();
    }
}