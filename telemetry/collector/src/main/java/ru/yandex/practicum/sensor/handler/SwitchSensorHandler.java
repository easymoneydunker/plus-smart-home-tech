package ru.yandex.practicum.sensor.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorEvent;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;
import ru.yandex.practicum.sensor.kafka.SensorProducer;

import java.time.Instant;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SwitchSensorHandler implements SensorHandler {

    private static final Logger logger = LoggerFactory.getLogger(SwitchSensorHandler.class);
    final SensorProducer producer;

    public SwitchSensorHandler(SensorProducer producer) {
        this.producer = producer;
    }

    @Override
    public SensorEventProto.PayloadCase getMessageType() {
        logger.debug("Getting message type: {}", SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT);
        return SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT;
    }

    @Override
    public void handle(SensorEventProto eventProto) {
        logger.info("Handling switch sensor event with ID: {}", eventProto.getId());

        SwitchSensorEvent switchSensorProto = eventProto.getSwitchSensorEvent();
        logger.debug("Switch event details: State = {}", switchSensorProto.getState());

        SensorEventAvro eventAvro = SensorEventAvro.newBuilder()
                .setId(eventProto.getId())
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()
                ))
                .setPayload(SwitchSensorAvro.newBuilder()
                        .setState(switchSensorProto.getState())
                        .build()
                )
                .build();

        logger.debug("Created Avro event: {}", eventAvro);
        producer.sendMessage(eventAvro);
        logger.info("Message sent to producer for event ID: {}", eventProto.getId());
    }
}
