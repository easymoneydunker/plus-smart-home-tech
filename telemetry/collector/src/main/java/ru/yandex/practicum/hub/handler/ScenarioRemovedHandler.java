package ru.yandex.practicum.hub.handler;

import ru.yandex.practicum.hub.kafka.HubProducer;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioRemovedHandler implements HubHandler {
    final HubProducer producer;

    public ScenarioRemovedHandler(HubProducer producer) {
        this.producer = producer;
    }

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEventProto eventProto) {
        ScenarioRemovedEventProto scenarioRemovedEventProto = eventProto.getScenarioRemoved();

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(eventProto.getTimestamp().getSeconds()))
                .setPayload(
                        ScenarioRemovedEventAvro.newBuilder()
                                .setName(scenarioRemovedEventProto.getName())
                                .build()
                )
                .build();

        producer.sendMessage(eventAvro);
    }
}